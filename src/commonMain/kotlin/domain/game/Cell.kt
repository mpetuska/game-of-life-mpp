package lt.petuska.game.of.life.mpp.domain.game

data class Cell(
    val x: Int,
    val y: Int,
    val isAlive: Boolean = false
)