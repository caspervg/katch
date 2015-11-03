package net.caspervg.katch

import net.caspervg.katch.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
open public class KatchController @Autowired constructor(val interactor: KatchInteractor) {

    private val NOT_FOUND = "Code with given ID not found"

    @RequestMapping("/code", method = arrayOf(RequestMethod.POST))
    public fun postCode(@RequestBody code: Code): Code {
        return interactor.add(code)
    }

    @RequestMapping("/code", method = arrayOf(RequestMethod.GET))
    public fun getCode(@RequestParam("max_age", required = false, defaultValue = "-1") maxAge: Long): List<Code> {
        if (maxAge < 0 || maxAge > Application.TIME_TO_LIVE) {
            return interactor.all();
        } else {
            val minTTL = Application.TIME_TO_LIVE - maxAge
            return interactor.all(minTTL);
        }
    }

    @RequestMapping("/code/{id}", method = arrayOf(RequestMethod.GET))
    public fun getCode(@PathVariable id: String): Code {
        if (interactor.exists(id)) {
            return interactor.get(id)
        } else {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    @RequestMapping("/code/{id}", method = arrayOf(RequestMethod.DELETE))
    public fun deleteCode(@PathVariable id: String) {
        if (interactor.exists(id)) {
            return interactor.remove(id)
        } else {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    @RequestMapping("/code/{id}", method = arrayOf(RequestMethod.PUT))
    public fun putCode(@RequestBody code: Code,
                       @PathVariable id: String): Code {
        if (interactor.exists(id)) {
            return interactor.edit(id, code);
        } else {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }



}