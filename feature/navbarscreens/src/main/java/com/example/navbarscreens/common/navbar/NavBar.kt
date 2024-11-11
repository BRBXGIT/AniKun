package com.example.navbarscreens.common.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.designsystem.icons.AniListIcons
import com.example.navbarscreens.anime_screen.navigation.AnimeScreenRoute
import com.example.navbarscreens.manga_screen.navigation.MangaScreenRoute
import com.example.navbarscreens.profile_screen.navigation.ProfileScreenRoute

data class NavItem(
    val label: String,
    val iconDefault: Int,
    val iconChosen: Int,
    val route: String,
    val destination: Any
)

@Composable
fun NavBar(
    navController: NavController
) {
    val navItems = listOf(
        NavItem(
            label = "Anime",
            iconDefault = AniListIcons.Cat,
            iconChosen = AniListIcons.CatFilled,
            route = "AnimeScreenRoute",
            destination = AnimeScreenRoute
        ),
        NavItem(
            label = "Manga",
            iconDefault = AniListIcons.Manga,
            iconChosen = AniListIcons.MangaFilled,
            route = "MangaScreenRoute",
            destination = MangaScreenRoute
        ),
        NavItem(
            label = "Profile",
            iconDefault = AniListIcons.User,
            iconChosen = AniListIcons.UserFilled,
            route = "ProfileScreenRoute",
            destination = ProfileScreenRoute
        )
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentRoute = if(currentDestination != null) currentDestination.toString().split(".")[5] else "AnimeScreenRoute"

    NavigationBar {
        navItems.forEach { navItem ->
            val chosen = currentRoute == navItem.route

            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if(!chosen) {
                        navController.navigate(navItem.destination)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = if(chosen) navItem.iconChosen else navItem.iconDefault),
                        contentDescription = null
                    )
                },
                label = {
                    Text(navItem.label)
                }
            )
        }
    }
}