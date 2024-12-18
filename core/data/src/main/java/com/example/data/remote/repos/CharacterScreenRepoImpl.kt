package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.character_info_response.CharacterDetailsResponse
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.repos.CharacterScreenRepo
import com.google.gson.Gson
import javax.inject.Inject

class CharacterScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): CharacterScreenRepo {

    override suspend fun getCharacterDetails(characterId: Int): CharacterDetailsResponse {
        val query = """
            query(${"$"}id: Int) {
              Character(id: ${"$"}id) {
                name {
                  native
                  full
                }
                image {
                  large
                }
                favourites
                description
                media {
                  nodes {
                    coverImage {
                      large
                    }
                    id
                    title {
                      romaji
                      english 
                    }
                    type
                  }
                }
              }
            }
        """.trimIndent()

        val variables = mapOf(
            "id" to characterId
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.getCharacterDetails(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
    }
}