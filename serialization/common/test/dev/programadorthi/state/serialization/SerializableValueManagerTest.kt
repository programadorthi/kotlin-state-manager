package dev.programadorthi.state.serialization

import dev.programadorthi.state.core.extension.basicValueManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class SerializableValueManagerTest {

    @Test
    fun shouldSerializeAndDeserialize() {
        val data = SerializableValueManager(
            intValue = basicValueManager(1),
            stringValue = basicValueManager("valueManager"),
            color = basicValueManager(Color("red")),
            composite = basicValueManager(
                basicValueManager(listOf(Color("blue"), Color("green")))
            )
        )
        val json = Json.encodeToString(data)
        val decoded = Json.decodeFromString<SerializableValueManager>(json)

        assertEquals(
            """{"intValue":1,"stringValue":"valueManager","color":{"name":"red"},"composite":[{"name":"blue"},{"name":"green"}]}""",
            json
        )
        assertEquals(data, decoded)
    }

}