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

  def main(args: Array[String]): Unit = {
    var argsList : Array[String] = null
    do {
      val line = scala.io.StdIn.readLine()

      argsList = line.split("[(, )]")

      val optBuilder: Option[AnyGateBuilder] = consumeFirst(argsList.head)
      if(argsList.head != "calculate") {
        if (optBuilder.isDefined) {
          consumeWhenBuilderDefined(optBuilder.get, argsList.tail)
        } else if (argsList(1) == "=") {
          val gateArgs = argsList.tail.tail
          val optBuilder: Option[AnyGateClsBuilder] = gateArgs.head match {
            case "OR" => Some(new OrClsBuilder)
            case "NOT" => Some(new NotClsBuilder)
            case "AND" => Some(new AndClsBuilder)
            case "CPY" => Some(new CopyClsBuilder)
            case _ => None
          }
          if (optBuilder.isDefined) {
            val builder = optBuilder.get
            builder.setOut(argsList.head, variables.toSet)
            putVariable(argsList.head)
            consumeWhenBuilderDefined(optBuilder.get, gateArgs.tail)
          } else {
            val value = gateArgs.head match {
              case "1" => Some(1)
              case "0" => Some(0)
              case _ => None
            }
            if (value.isDefined && AnyGateBuilder.checkVariable(argsList.head,  variables.toSet)) {
              putVariable(argsList.head, value.get)
            } else {
              throw new UnknownValue(gateArgs.head)
            }
          }
        } else {
          throw new UnknownCommand(argsList.head)
        }
      }
    } while(argsList.head != "calculate")
    gates = gates.reverse
    variables = variables.reverse
    values = values.reverse

    print(new CircuitExecutor(new Circuit(gates.flatMap(g => g.toQuantumGate(variables.indexOf)), variables.size), values).toString())
  }


  def consumeFirst(param: String): Option[AnyGateBuilder] =
    param match {
      case "FRD" => Some(new FredkinQBuilder)
      case "HDM" => Some(new HadamardQBuilder)
      case "NOT" => Some(new NotQBuilder)
      case "TFL" => Some(new ToffoliQBuilder)
      case _ => None
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
