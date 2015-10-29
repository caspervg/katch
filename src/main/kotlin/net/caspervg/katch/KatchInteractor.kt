package net.caspervg.katch

interface KatchInteractor {

    fun add(code: Code): Code
    fun exists(id: String): Boolean
    fun get(id: String): Code
    fun edit(id: String, code: Code): Code
    fun remove(id: String)
    fun all(): List<Code>

}