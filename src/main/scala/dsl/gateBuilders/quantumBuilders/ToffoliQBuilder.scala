package dsl.gateBuilders.quantumBuilders

import dsl.gateBuilders.AnyGateBuilder
import dsl.gates.DSLAnyGate
import dsl.gates.dslquantum.ToffoliQ

class ToffoliQBuilder extends ControlGateQBuilder(Some(1), None, "TLF"){
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new ToffoliQ(controls, args.head)
  }
}
