package com.example.data.repos

import com.example.data.remote.models.character_info_response.CharacterDetailsResponse

interface CharacterScreenRepo {

    suspend fun getCharacterDetails(characterId: Int): CharacterDetailsResponse
}