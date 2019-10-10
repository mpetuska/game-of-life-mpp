package lt.petuska.game.of.life.mpp.ui.model

interface CellModel : Model {
    var x: Int
    var y: Int
    var isAlive: Boolean
}