package com.viled.feature_job

object MockData {

    private const val description =
        "- 3+ years of professional Android software development\\n- Experience with Kotlin or Java \\n- Solid understanding of programming and computer science fundamentals\\n- Experience in multi-threading, memory management, and network and cache optimization specific to mobile devices\\n- MVVM or MVP architecture in Android a plus\\n- Experience with RxJava a nice-to-have\\n- Knowledge of Android SDK performance tools and optimization techniques"

    fun getLocalJobs(): List<Job> {
        val company = Company(
            name = "Tinkoff",
            description = "Tinkoff bank is a rapidly growing product development firm located in the heart of downtown Moscow. We partner with the world’s leading organizations to build software-powered products that are enjoyed by millions of users globally.",
            slides = listOf(
                "https://www.officenext.ru/upload/resize/68/687f91302f397f2b1689f32b13e98d67_880x630.jpg",
                "https://expertpobonusam.com/sites/default/files/images/bank-160-1556.jpg",
                "https://cdn.forbes.ru/files/presets/900_566/story_images/29962__vid204601e.jpg",
                "https://s0.rbk.ru/v6_top_pics/media/img/8/72/754441390063728.jpg"
            )
        )

        return listOf(
            Job(
                id = "AA",
                company = company,
                salary = "120 000 RUB / month",
                location = "Moscow, Russia",
                positionName = "Senior Android Dev",
                positionDescription = description,
                colleges = emptyList(),
                isActive = true
            ),
            Job(
                id = "BB",
                company = company,
                salary = "120 000 RUB / month",
                location = "Moscow, Russia",
                positionName = "Senior Android Dev",
                positionDescription = description,
                colleges = emptyList(),
                isActive = true
            ),
            Job(
                id = "CC",
                company = company,
                salary = "115 000 RUB / month",
                location = "Moscow, Russia",
                positionName = "Senior Android Dev",
                positionDescription = description,
                colleges = emptyList(),
                isActive = true
            )
        )
    }

    fun getForeignJobs(): List<Job> {
        val company = Company(
            name = "Chase",
            description = "Chase bank is a rapidly growing product development firm located in the heart of downtown New York. We partner with the world’s leading organizations to build software-powered products that are enjoyed by millions of users globally.",
            slides = listOf(
                "http://wikibusiness.ru/wp-content/uploads/2018/05/JPMorgan-Chase.jpg",
                "https://i.insider.com/5b2a9aff1ae6621b008b5175?width=1300&format=jpeg&auto=webp",
                "https://i.insider.com/5b2ac5961ae6621c008b464d?width=1300&format=jpeg&auto=webp",
                "https://i.insider.com/5b2a9afe1ae6621c008b54af?width=1300&format=jpeg&auto=webp"
            )
        )

        return listOf(
            Job(
                id = "1",
                company = company,
                salary = "120 000 USD/yearly",
                location = "New York, USA",
                positionName = "Senior Android Dev",
                positionDescription = description,
                colleges = listOf(),
                isActive = true
            ),
            Job(
                id = "a",
                company = company,
                salary = "115 000 USD/yearly",
                location = "New York, USA",
                positionName = "Senior Android Dev",
                positionDescription = description,
                colleges = listOf(),
                isActive = true
            ),
            Job(
                id = "3",
                company = company,
                salary = "100 000 USD/yearly",
                location = "New York, USA",
                positionName = "Middle Android Dev",
                positionDescription = description,
                colleges = listOf(),
                isActive = true
            )
        )
    }
}