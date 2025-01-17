package center.sciprog.tasks_bot.webapp.client

import center.sciprog.tasks_bot.webapp.common.DefaultClient
import dev.inmo.micro_utils.startup.plugin.StartPlugin
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.web.renderComposable
import org.koin.core.Koin
import org.koin.core.module.Module

object JsPlugin : StartPlugin {
    override fun Module.setupDI(config: JsonObject) {
        with(CommonPlugin) { setupDI(config) }
    }
    override suspend fun startPlugin(koin: Koin) {
        super.startPlugin(koin)
        with(CommonPlugin) { startPlugin(koin) }
        renderComposable("root") {
            WebAppContent(koin.get<DefaultClient>())
        }
    }
}
