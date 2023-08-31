package me.jimmy.blog

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(
    private val articleRepository: ArticleRepository) {

    @GetMapping("/")
    fun findAll() = articleRepository.findAllByOrderByAddedAtDesc()

//    @GetMapping("/{slug}")
//    fun findOne(@PathVariable slug: String): Article {
//        return articleRepository.findBySlug(slug)
//            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
//    }

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) =
        articleRepository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")

}

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userRepository: UserRepository) {

    @GetMapping("/")
    fun findAll(): Iterable<User> = userRepository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) = userRepository.findByLogin(login)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")

}