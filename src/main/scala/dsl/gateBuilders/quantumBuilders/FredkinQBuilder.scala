package dsl.gateBuilders.quantumBuilders

import dsl.gateBuilders.AnyGateBuilder
import dsl.gates.DSLAnyGate
import dsl.gates.dslquantum.FredkinQ

class FredkinQBuilder extends AnyGateBuilder(Some(3), "FRD") {
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new FredkinQ(args.head, (args(1), args(2)))
  }
}
