package dsl

import dsl.exceptions.{UnknownCommand, UnknownValue}
import dsl.gateBuilders.AnyGateBuilder
import dsl.gateBuilders.classicBuilders._
import dsl.gateBuilders.quantumBuilders._
import dsl.gates.DSLAnyGate
import quantum.CircuitExecutor
import quantum.circuit.Circuit


object Parser {
  var gates: List[DSLAnyGate] = List()
  var values : List[Int] = List()
  var variables : List[String] = List()
  var argsList: Array[String] = _

  def main(args: Array[String]): Unit = {
    do {
      try {
        getCommand()
      } catch {
        case e : RuntimeException => print(e.getMessage)
      }
    } while(argsList.head != "calculate")

    gates = gates.reverse
    variables = variables.reverse
    values = values.reverse

    print(new CircuitExecutor(new Circuit(gates.flatMap(g => g.toQuantumGate(variables.indexOf)), variables.size), values).toString())
  }

  def getCommand(): Unit = {
    val line = scala.io.StdIn.readLine()
    argsList = line.split("[(, )]").map( e => e.trim)

    if(argsList.head != "calculate") {
      val optBuilder: Option[AnyGateBuilder] = getBuilderForQuantumGate(argsList.head)
      if (optBuilder.isDefined) {
        consumeWhenBuilderDefined(optBuilder.get, argsList.tail)
      } else if (argsList(1) == "=") {
        val varName = argsList.head
        argsList = argsList.tail.tail
        val optBuilder: Option[AnyGateClsBuilder] = getBuilderForClassicalGate(argsList.head)
        if (optBuilder.isDefined) {
          buildClassicalGate(optBuilder.get, argsList.tail, varName)
        } else {
          assignValueToVariable(argsList.head, varName)
        }
      } else {
        throw new UnknownCommand(argsList.head)
      }
    }
  }


  def getBuilderForQuantumGate(param: String): Option[AnyGateBuilder] =
    param match {
      case "FRD" => Some(new FredkinQBuilder)
      case "HDM" => Some(new HadamardQBuilder)
      case "NOT" => Some(new NotQBuilder)
      case "TFL" => Some(new ToffoliQBuilder)
      case _ => None
    }

  def getBuilderForClassicalGate(param: String): Option[AnyGateClsBuilder] =
    param match {
      case "OR" => Some(new OrClsBuilder)
      case "NOT" => Some(new NotClsBuilder)
      case "AND" => Some(new AndClsBuilder)
      case "CPY" => Some(new CopyClsBuilder)
      case _ => None
    }

  def buildClassicalGate(builder: AnyGateClsBuilder, args: Array[String], varName: String): Unit = {
    builder.setOut(varName, variables.toSet)
    putVariable(varName)
    consumeWhenBuilderDefined(builder, args)
  }

  def assignValueToVariable(arg: String, varName: String): Unit = {
    val value = arg match {
      case "1" => Some(1)
      case "0" => Some(0)
      case _ => None
    }

    if (value.isDefined && AnyGateBuilder.checkVariable(varName,  variables.toSet)) {
      putVariable(varName, value.get)
    } else {
      throw new UnknownValue(arg)
    }
  }

  def consumeWhenBuilderDefined(builder: AnyGateBuilder, params: Array[String]): Unit = {
    params.foreach(builder.consume(_, variables.toSet))
    gates +:= builder.getGate
  }

  def putVariable(name: String, value: Int = 0): Unit = {
    values +:= value
    variables +:= name
  }
}
