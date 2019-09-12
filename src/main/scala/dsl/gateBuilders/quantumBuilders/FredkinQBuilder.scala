package dsl.gateBuilders.quantumBuilders

import dsl.gates.DSLAnyGate
import dsl.gates.dslquantum.FredkinQ

class FredkinQBuilder extends ControlGateQBuilder(Some(2), Some(1),  "FRD") {
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new FredkinQ(controls.head, (args(0), args(1)))
  }
}
