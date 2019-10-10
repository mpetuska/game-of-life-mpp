package lt.petuska.game.of.life.mpp.ui.model

interface ControlModel : Model {
    var universeWidth: Int
    var universeHeight: Int
    var fps: Int
    var isTimeRunning: Boolean
}