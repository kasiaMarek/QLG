package dsl.gateBuilders.quantumBuilders

import dsl.gateBuilders.AnyGateBuilder
import dsl.gates.DSLAnyGate
import dsl.gates.dslquantum.NotQ

class NotQBuilder extends AnyGateBuilder(Some(1), "NOT"){
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new NotQ(args.head)
  }
}
