package net.caspervg.katch

import org.redisson.Redisson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class KatchDAOImpl @Autowired constructor (val redis: Redisson) : KatchDAO {

    private val BUCKET_PREFIX = "katch_"
    private val NOT_PRESENT = "Document with given ID was not present"

    override fun add(code: Code): String {
        val uuid = UUID.randomUUID().toString()
        val bucket = redis.getBucket<Code>(BUCKET_PREFIX + uuid)
        code.identifier = uuid

        bucket.set(code)
        return uuid
    }

    override fun exists(id: String): Boolean {
        val bucket = redis.getBucket<Code>(BUCKET_PREFIX + id)

        return bucket.exists()
    }

    override fun get(id: String): Code {
        val bucket = redis.getBucket<Code>(BUCKET_PREFIX + id)

        if (bucket.exists()) {
            return bucket.get()
        } else {
            throw RuntimeException(NOT_PRESENT)
        }
    }

    override fun edit(id: String, replacement: Code) {
        val bucket = redis.getBucket<Code>(BUCKET_PREFIX + id)

        if (bucket.exists()) {
            val old = bucket.get()
            replacement.identifier = old.identifier

            bucket.set(replacement)
        } else {
            throw RuntimeException(NOT_PRESENT)
        }
    }

    override fun delete(id: String) {
        val bucket = redis.getBucket<Code>(BUCKET_PREFIX + id)

        bucket.delete();
    }

    override fun all(): List<Code> {
        val codes = redis.keys.getKeysByPattern(BUCKET_PREFIX + "*")
        val ret = ArrayList<Code>()

        codes.forEach {
            val bucket = redis.getBucket<Code>(it)
            if (bucket.exists()) {
                ret.add(bucket.get())
            }
        }

        return ret
    }

}