package net.caspervg.katch

interface KatchDAO {

    fun add(code: Code): String
    fun exists(id: String): Boolean
    fun get(id: String): Code
    fun edit(id: String, replacement: Code)
    fun delete(id: String)
    fun all(): List<Code>

}