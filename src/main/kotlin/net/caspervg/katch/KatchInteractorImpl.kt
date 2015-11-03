package net.caspervg.katch

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KatchInteractorImpl @Autowired constructor (val dao: KatchDAO) : KatchInteractor {

    override fun add(code: Code): Code {
        val id = dao.add(code)
        return dao.get(id)
    }

    override fun exists(id: String): Boolean {
        return dao.exists(id);
    }

    override fun get(id: String): Code {
        return dao.get(id);
    }

    override fun edit(id: String, code: Code): Code {
        dao.edit(id, code);
        return dao.get(id);
    }

    override fun remove(id: String) {
        dao.delete(id)
    }

    override fun all(minTTL: Long): List<Code> {
        return dao.all(minTTL);
    }

}