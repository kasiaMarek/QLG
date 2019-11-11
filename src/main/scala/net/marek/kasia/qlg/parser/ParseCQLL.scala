package net.marek.kasia.qlg.parser

import net.marek.kasia.qlg.exorcism.FunctionOptimization
import net.marek.kasia.qlg.parser.exceptions.{ArgumentAlreadyDefinedException, VariableNotDefinedException}
import net.marek.kasia.qlg.quantum.CircuitExecutor
import net.marek.kasia.qlg.quantum.circuit.Circuit

object ParseCQLL {

  def parseToCircuitExec(p: List[Expression]): CircuitExecutor = ParseStateParser.parse(parse(p))

  def parse(p: List[Expression]): ParseState = p.foldLeft(new ParseState(List(), List(), 0))((ps, e) => parse(ps, e))

  def parse(state: ParseState, e: Expression): ParseState = {
    e match {
      case Attribution(v, va) => {
        if(state.varInfo.exists(_.name == v.name)) {
          throw new ArgumentAlreadyDefinedException(v.name)
        } else {
          va match {
            case One() => state ++ VarInfo(v.name, 1)
            case Zero() => state ++ VarInfo(v.name, 0)
            case v1: Variable => state ++ new ToffoliQ(List(parseVar(state, v1)), v.name) ++ VarInfo(v.name, 0)
            case clsGate: BoolFunction =>
              FunctionOptimization
                .toQuantumGates(clsGate, v)
                .foldLeft(state ++ VarInfo(v.name, 0))((s, g) => parse(s,g))
          }
        }
      }
      case qg: QGate => parse(state, qg)
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

case class VarInfo(name: String, init: Int)

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
