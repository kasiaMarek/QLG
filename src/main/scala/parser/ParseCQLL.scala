package parser

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter, InputStreamReader, OutputStreamWriter}

import parser.exceptions.{ArgumentAlreadyDefinedException, VariableNotDefinedException}
import quantum.CircuitExecutor
import quantum.circuit.Circuit

class ParseCQLL(inFile: Option[String] = Option.empty, outFile: Option[String] = Option.empty) extends CQLL{
  val reader = new BufferedReader (if (inFile.isEmpty) new InputStreamReader(System.in) else new FileReader(inFile.get))
  val writer = new BufferedWriter(if (outFile.isEmpty) new OutputStreamWriter(System.out) else new FileWriter(outFile.get))

  def parse(): Unit = {
    try {
      val parsed = parseAll(program, reader)
      println(parsed)
      val p = parse(parsed.get)
      writer.write(ParseStateParser.parse(p).toString())
      writer.flush()
      writer.close()
    } catch {
      case e : RuntimeException => e.printStackTrace()
    }
  }

  def parse(p: List[Expression]): ParseState = p.foldLeft(new ParseState(List(), List(), 0))((ps, e) => parse(ps, e))

  def parse(state: ParseState, e: Expression): ParseState = {
    e match {
      case Attribution(v, va, isResult) => {
        if(state.varInfo.exists(_.name == v.name)) {
          throw new ArgumentAlreadyDefinedException(v.name)
        } else {
          va match {
            case One() => state ++ VarInfo(v.name, 1, isResult)
            case Zero() => state ++ VarInfo(v.name, 0, isResult)
            case clsGate: ClsGate => parse(state, clsGate, v.name) ++ VarInfo(v.name, 0, isResult)
          }
        }
      }
      case qg: QGate => parse(state, qg)
    }
  }

  def parse(state: ParseState, e: ClsGate, to:String): ParseState = {
    e match {
      case Copy(v) => state ++ new CopyCls(parseVar(state, v), to)
      case Not(v) => {
        val (ps, n) = parse(state, v)
        ps ++ new NotCls(n, to)
      }
      case And(l) => {
        val (ps1, argsList) = l.foldLeft((state, List[String]()))((acc, e) => {
          val (ps, n) = parse(acc._1, e)
          (ps, acc._2 :+ n)
        })
        ps1 ++ new AndCls(argsList, to)
      }
      case Or(l) => {
        val (ps1, argsList) = l.foldLeft((state, List[String]()))((acc, e) => {
          val (ps, n) = parse(acc._1, e)
          (ps, acc._2 :+ n)
        })
        ps1 ++ new OrCls(argsList, to)
      }
    }
  }

  def parse(state: ParseState, e: V): (ParseState, String) = {
      e match {
      case v: Variable => (state, parseVar(state, v))
      case One() => {
        val tmp = state.getTmp
        (state.nextTmp() ++ VarInfo(tmp, 1, false), tmp)
      }
      case Zero() => {
        val tmp = state.getTmp
        (state.nextTmp() ++ VarInfo(tmp, 0, false), tmp)
      }
      case cls: ClsGate => {
        val tmp = state.getTmp
        (parse(state.nextTmp(), cls, tmp) ++ VarInfo(tmp, 0, false), tmp)
      }
    }
  }

  def parse(state: ParseState, e: QGate): ParseState = {
    e match {
      case Hdm(v) => state ++ new HadamardQ(parseVar(state, v))
      case Tfl(c, v) => state ++ new ToffoliQ(c.map(parseVar(state, _)), parseVar(state, v))
      case Frd(c, v1, v2) => state ++ new FredkinQ(c.map(parseVar(state, _)), (parseVar(state, v1), parseVar(state, v2)))
      case NotQ(v) => state ++ new NotQDSL(parseVar(state, v))
    }
  }

  def parseVar(state: ParseState, e: Variable): String = {
    if(state.varInfo.exists(_.name == e.name)) {
      e.name
    } else {
      throw new VariableNotDefinedException(e.name)
    }
  }

}

case class VarInfo(name: String, init: Int, isRes: Boolean)

class ParseState(val varInfo: List[VarInfo], val gates: List[DSLAnyGate], val tmp: Int) {
  def ++(gate: DSLAnyGate): ParseState = new ParseState(varInfo, gates :+ gate, tmp)
  def ++(newVar: VarInfo): ParseState = new ParseState(varInfo :+ newVar, gates, tmp)
  def getTmp: String = "$tmp_" + tmp
  def nextTmp(): ParseState = new ParseState(varInfo, gates, tmp + 1)
}

object ParseStateParser {
  def parse(ps: ParseState): CircuitExecutor = {
    val size = ps.varInfo.size
    val map = (name: String) => ps.varInfo.zipWithIndex.map(e => e._1.name -> e._2).toMap.getOrElse(name, -1)
    val circuit = new Circuit(ps.gates.flatMap(_.toCircuitGateList(map)), size)
    new CircuitExecutor(circuit, ps.varInfo.map(_.init), ps.varInfo.map(_.name))
  }
}
