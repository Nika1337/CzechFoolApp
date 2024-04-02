# Czech Fool
"Czech Fool" is a multi-modular application designed for tracking the card game "Czech Fool". Initially Developed for personal use and to enhance programming skills, the app offers a seamless experience across various devices, catering to both larger screens and smaller ones, including foldable devices. Notably, the application is fully optimized to handle process death scenarios efficiently. Vital components are fully tested.

## Modularization
The app follows contains following types of modules:
- The `app`module - binds all other modules together, is an entry point for the app and controls navigation.
- `feature:` modules - feature specific modules which are scoped to handle a single responsibility
- `core:` modules - common library modules containing auxiliary code and specific dependencies that need to be shared between other modules in the app
## Architecture
The Czech Fool app follows the [official architecture guidance](https://developer.android.com/topic/architecture). MVVM pattern is used. SOLID principles are closely followed.
## Technologies
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Library](https://developer.android.com/jetpack/androidx/releases/room)
- [Dagger-Hilt](https://dagger.dev/hilt/)

## Challenges Faced
- How to share data between ViewModels
- Should dynamically connected screens have one or multiple ViewModel
- How to join tables using Room library
- How to save state using SavedStateHandle
- How to stop compose from recomposing TextFields which didn't change
- Database normalization
- How to modularize app, how to create convention plugins for gradle

## Features
This app contains four main routes: <u>Games History</u>, <u>Game Options</u>, <u>Name Input</u>, <u>Game</u>. Some routes have two screens, which are connected using dynamic navigation (for different screen sizes), while others have one screen.
### App Scaffolding
Package [`com.example.czechfoolapp`](app/src/main/java/com/example/czechfoolapp/ui/routes/gameshistoryroute)
[`MainActivity.kt`](app/src/main/java/com/example/czechfoolapp/MainActivity.kt) is applications entry point.
[`Navigation.kt`](app/src/main/java/com/example/czechfoolapp/Navigation.kt) is responsible for navigation in the app.

### Screenshots And Brief Descriptions

#### Games History Route
Package [`com.example.czechfoolapp.ui.routes.gameshistoryroute`](app/src/main/java/com/example/czechfoolapp/ui/routes/gameshistoryroute)
#### Games History
Package [`com.example.czechfoolapp.feature.gameshistory`](feature/gameshistory/src/main/kotlin/com/feature/gameshistory)
<u>Games History Route</u> has two screens, <u>Game List Screen</u> and <u>Game Detail Screen</u>.
<u>Game List Screen</u> shows list of games, when a game is clicked <u>Game Detail Screen</u> is shown, which displays more information about the game (currently, almost the same information). <u>Game Detail Screen</u> has the continue button using which game can be continued (it's disabled when Game is finished).
<p style=float:left">
  <img src="Screenshots/GamesHistoryCompact.jpg" height="400" />
  <img src="Screenshots/GameDetailCompact.jpg" height="400" />
  <img src="Screenshots/GamesHistoryMedium.jpg" height="400" />
</p>

#### Game Options
Package [`com.example.czechfoolapp.feature.gameoptions`](feature/gameoptions/src/main/kotlin/com/feature/gameoptions)
<u>Game Options Route</u> has one screen, where user inputs losing score and number of players.
<p style=float:left">
  <img src="Screenshots/GameOptionsCompact.jpg" height="400" />
</p>


#### Name Input
Package [`com.example.czechfoolapp.feature.nameinput`](feature/nameinput/src/main/kotlin/com/feature/nameinput)
<u>Name Input Route</u> has one screen, where user inputs names of players.
<p style=float:left">
  <img src="Screenshots/NameInputCompact.jpg" height="400" />
</p>

#### Game
Package [`com.example.czechfoolapp.feature.game`](feature/game/src/main/kotlin/com/feature/game)
<u>Game Route</u> has two screens, <u>Game Progress Screen</u> and <u>Card Choice Screen</u>.
<u>Game Progress Screen</u> shows players and their scores (different colors show that player is winner or player score has been set for current round).  When player is clicked <u>Card Choice State</u> is shown, if the player is winner, the card player won with should be inputted, else all cards are shown can be chosen. after at least one card was chosen and done was clicked, players color changes and score is added, after all player scores have been inputted and the done was clicked, round is over and it starts all over again until at least one player loses or game is canceled.
<p style=float:left">
  <img src="Screenshots/GameProgressCompact.jpg" height="400" />
  <img src="Screenshots/CardChoiceCompact.jpg" height="400" />
  <img src="Screenshots/GameProgress_CardChoice.jpg" height="400" />
  <img src="Screenshots/GameProgressMedium.jpg" height="400" />
</p>

## Layers
--- 
### Database
Package [`com.example.czechfoolapp.core.database`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database)
Database has two entities, [`GameEntity`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/model/GameEntity.kt) and a weak entity [`PlayerEntity`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/model/PlayerEntity.kt) dependent on [`GameEntity`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/model/GameEntity.kt). [`GameWithPlayers`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/model/GameWithPlayers.kt) represents natural join between [`GameEntity`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/model/GameEntity.kt) and [`PlayerEntity`](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/model/PlayerEntity.kt) which is used for querying game table.

### Data
Package [`com.example.czechfoolapp.core.data`](core/database/src/main/kotlin/com/example/czechfoolapp/core/data)
Data layer consists of repositories, which are responsible for communicating with data sources. All repositories are defined as an interfaces and then default implementations are provided, which is useful for dependency injection and testing.


### Domain
Package [`com.example.czechfoolapp.core.domain`](core/database/src/main/kotlin/com/example/czechfoolapp/core/domain)
Package [`validation`](core/database/src/main/kotlin/com/example/czechfoolapp/core/domain/validation) has use cases for validating user input.
[StartNewGameUseCase](core/database/src/main/kotlin/com/example/czechfoolapp/core/database/StartNewGameUseCase.kt) stores new game and its players persistently



