package me.jimmy.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllersTests(
    @Autowired val mvc: MockMvc) {

    @MockkBean
    lateinit var userRepository: UserRepository

    @MockkBean
    lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val jimmy = User("jamesMcGill", "James", "McGill")

        val article1 = Article("title", "headline", "content", jimmy)
        val article2 = Article("title2", "headline2", "content2", jimmy)

        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(article1, article2)

        mvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(jimmy.login))
            .andExpect(jsonPath("\$.[0].slug").value(article1.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(jimmy.login))
            .andExpect(jsonPath("\$.[1].slug").value(article2.slug))
    }

    @Test
    fun `List users`() {
        val jimmy = User("jamesMcGill", "James", "McGill")
        val kim = User("kimWexler", "Kim", "Wexler")

        every { userRepository.findAll() } returns listOf(jimmy, kim)

        mvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(jimmy.login))
            .andExpect(jsonPath("\$.[1].login").value(kim.login))
    }

}