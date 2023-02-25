package center.sciprog.tasks_bot.teachers

import center.sciprog.tasks_bot.common.useCache
import center.sciprog.tasks_bot.teachers.repos.CachedTeachersRepo
import center.sciprog.tasks_bot.teachers.repos.ExposedTeachersRepo
import center.sciprog.tasks_bot.teachers.repos.TeachersRepo
import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.plagubot.Plugin
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContextWithFSM
import kotlinx.serialization.json.JsonObject
import org.jetbrains.exposed.sql.Database
import org.koin.core.Koin
import org.koin.core.module.Module

object JvmPlugin : Plugin {
    override fun Module.setupDI(database: Database, params: JsonObject) {
        with(CommonPlugin) { setupDI(database, params) }

        single { ExposedTeachersRepo(get()) }

        single<TeachersRepo> {
            val base = get<ExposedTeachersRepo>()
            if (useCache) {
                CachedTeachersRepo(base, get())
            } else {
                base
            }
        }
    }

    override suspend fun BehaviourContextWithFSM<State>.setupBotPlugin(koin: Koin) {
        with(CommonPlugin) { setupBotPlugin(koin) }
    }
}
