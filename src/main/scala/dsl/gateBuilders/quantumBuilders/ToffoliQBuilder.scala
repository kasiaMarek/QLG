package dsl.gateBuilders.quantumBuilders

import dsl.gateBuilders.AnyGateBuilder
import dsl.gates.DSLAnyGate
import dsl.gates.dslquantum.ToffoliQ

class ToffoliQBuilder extends AnyGateBuilder(None, "TLF"){
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new ToffoliQ(args.tail, args.head)
  }
}
