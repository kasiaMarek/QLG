package dsl

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter, InputStreamReader, OutputStreamWriter}
import dsl.exceptions.{UnknownCommand, UnknownValue}
import dsl.gateBuilders.AnyGateBuilder
import dsl.gateBuilders.classicBuilders._
import dsl.gateBuilders.quantumBuilders._
import dsl.gates.DSLAnyGate
import quantum.CircuitExecutor
import quantum.circuit.Circuit

class Parser(inFile: Option[String] = Option.empty, outFile: Option[String] = Option.empty) {
  val reader = new BufferedReader (if (inFile.isEmpty) new InputStreamReader(System.in) else new FileReader(inFile.get))
  val writer = new BufferedWriter(if (outFile.isEmpty) new OutputStreamWriter(System.out) else new FileWriter(outFile.get))

  var gates: List[DSLAnyGate] = List()
  var values: List[Int] = List()
  var variables: List[String] = List()

  def parse(): Unit = {
    var argsList: Array[String] = getArgsList()

    while (argsList.head != "calculate") {
      try {
        getCommand(argsList)
        argsList = getArgsList()
      } catch {
        case e: RuntimeException => {
          writer.write(e.getMessage)
          writer.flush()
        }
      }
    }

    gates = gates.reverse
    variables = variables.reverse
    values = values.reverse

    writer.write(new CircuitExecutor(new Circuit(gates.flatMap(g => g.toQuantumGate(variables.indexOf)), variables.size), values, variables).toString())
    writer.flush()
  }

  private def getArgsList(): Array[String] = reader.readLine().split("[(, )]").filter(e => e != "")

  private def getCommand(argsList: Array[String]): Unit = {
    val optBuilder: Option[AnyGateBuilder] = getBuilderForQuantumGate(argsList.head)
    if (optBuilder.isDefined) {
      consumeWhenBuilderDefined(optBuilder.get, argsList.tail)
    } else if (argsList(1) == "=") {
      getCommandIfAssignedToVariable(argsList.head, argsList.tail.tail)
    } else {
      throw new UnknownCommand(argsList.head)
    }
  }

  private def getCommandIfAssignedToVariable(varName: String, argsList: Array[String]): Unit = {
    val optBuilder: Option[AnyGateClsBuilder] = getBuilderForClassicalGate(argsList.head)
    if (optBuilder.isDefined) {
      buildClassicalGate(optBuilder.get, argsList.tail, varName)
    } else {
      assignValueToVariable(argsList.head, varName)
    }
  }


  private def getBuilderForQuantumGate(param: String): Option[AnyGateBuilder] =
    param match {
      case "FRD" => Some(new FredkinQBuilder)
      case "HDM" => Some(new HadamardQBuilder)
      case "NOT" => Some(new NotQBuilder)
      case "TFL" => Some(new ToffoliQBuilder)
      case _ => None
    }

  private def getBuilderForClassicalGate(param: String): Option[AnyGateClsBuilder] =
    param match {
      case "OR" => Some(new OrClsBuilder)
      case "NOT" => Some(new NotClsBuilder)
      case "AND" => Some(new AndClsBuilder)
      case "CPY" => Some(new CopyClsBuilder)
      case _ => None
    }

  private def buildClassicalGate(builder: AnyGateClsBuilder, args: Array[String], varName: String): Unit = {
    builder.setOut(varName, variables.toSet)
    putVariable(varName)
    consumeWhenBuilderDefined(builder, args)
  }

  private def assignValueToVariable(arg: String, varName: String): Unit = {
    val value = arg match {
      case "1" => Some(1)
      case "0" => Some(0)
      case _ => None
    }

    if (value.isDefined && AnyGateBuilder.checkVariable(varName, variables.toSet)) {
      putVariable(varName, value.get)
    } else {
      throw new UnknownValue(arg)
    }
  }

  private def consumeWhenBuilderDefined(builder: AnyGateBuilder, params: Array[String]): Unit = {
    params.foreach(builder.consume(_, variables.toSet))
    gates +:= builder.getGate
  }

  private def putVariable(name: String, value: Int = 0): Unit = {
    values +:= value
    variables +:= name
  }
}
