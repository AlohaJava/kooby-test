package app

import io.jooby.Context
import io.jooby.MediaType
import io.jooby.StatusCode
import io.jooby.annotation.Consumes
import io.jooby.annotation.GET
import io.jooby.annotation.POST
import io.jooby.annotation.QueryParam
import io.jooby.jackson.JacksonModule
import io.jooby.runApp
import kotlinx.coroutines.delay
import java.util.*


class Message(var message: String? = null)
data class Time(
    val time: Date
)

class Controller {
    @GET("/")
    fun say() =
        Message("Ещё есть /time, /sayHiTo?name={name}, /error, /delayed, POST /test-post(одно поле message с текстом)")

    @GET("/time")
    fun time() = Time(Date())

    @GET("/sayHiTo")
    fun sayHi(@QueryParam name: String) = Message("Привет $name")

    @GET("/error")
    fun error(ctx: Context): Message {
        ctx.responseCode = StatusCode.SERVER_ERROR
        return Message("Нихуя")
    }

    @GET("/delayed")
    suspend fun delayed(ctx: Context): String {
        delay(777)
        return ctx.requestPath
    }

    @POST("/test-post")
    @Consumes(MediaType.JSON)
    fun testPost(ctx: Context) = ctx.body(Message::class.java)


}

fun main(args: Array<String>) {
    runApp(args) {
        install(JacksonModule())
        mvc(Controller())
    }
}