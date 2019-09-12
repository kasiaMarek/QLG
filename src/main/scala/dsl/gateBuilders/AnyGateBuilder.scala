package dsl.gateBuilders

import dsl.exceptions.{NotEnoughArgumentsException, TooManyArgumentsException, VariableNotDefinedException}
import dsl.gates.DSLAnyGate

abstract class AnyGateBuilder(val numOfArgs: Option[Int], val gateName: String) {
  var args: List[String] = List()

  def consume(arg: String, declaredArgs: Set[String]): Unit = {
    if(AnyGateBuilder.checkArgument(arg, declaredArgs)) {
      args :+= arg
    } else {
      throw  new VariableNotDefinedException(arg)
    }
  }

  def beforeGetGate: Unit = {
    checkArgNum(args.size, numOfArgs)
  }

  def checkArgNum(actual: Int, expected: Option[Int]): Unit = {
    if(expected.isDefined && actual < expected.get) {
      throw new NotEnoughArgumentsException(gateName, expected.get)
    } else {
      if(expected.isDefined && actual > expected.get) {
        throw new TooManyArgumentsException(gateName, expected.get)
      }
    }
  }

  def getGate: DSLAnyGate

}

object AnyGateBuilder {
  def checkArgument(arg: String, declaredArgs: Set[String]): Boolean = declaredArgs.contains(arg)
  def checkVariable(arg: String, declaredArgs: Set[String]): Boolean =
    arg.matches("[a-z_][a-zA-Z_]*") && !declaredArgs.contains(arg)
}
