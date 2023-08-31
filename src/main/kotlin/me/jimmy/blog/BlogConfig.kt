package me.jimmy.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfig {

    @Bean
    fun dbInit(
        userRepository: UserRepository,
        articleRepository: ArticleRepository) = ApplicationRunner {

            val jimmy = userRepository.save(User(
                login = "jamesMcGill",
                firstname = "James",
                lastname = "McGill"
            ))

            articleRepository.save(Article(
                title = "title",
                headline = "headline",
                content = "content",
                author = jimmy
            ))

            articleRepository.save(Article(
                title = "title2",
                headline = "headline2",
                content = "content2",
                author = jimmy
            ))
    }

}