package lt.petuska.gol.game

import lt.petuska.gol.redux.AppConfigStoreImpl
import lt.petuska.gol.redux.UniverseStoreImpl


expect fun startEngine(appConfigStore: AppConfigStoreImpl, universeStore: UniverseStoreImpl)