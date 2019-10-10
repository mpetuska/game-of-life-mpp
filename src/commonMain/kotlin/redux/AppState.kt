package lt.petuska.game.of.life.mpp.redux

data class AppState(
    val fps: Int = 10,
    val isTimeRunning: Boolean = false,
    val universeWidth: Int = 15,
    val universeHeight: Int = 20
)