interface SuggestionController<T> {
    fun search(item: String): List<String>
    fun printSelectedItem(interestedItem: String)
    fun recent(recentItem: String): List<String>
}

class User(
    val id: String,
    val userName: String,
    val name: String,
    val surname: String
)

class Tag(
    val id: String,
    val hashTag: String
)

class UserSuggestionController : SuggestionController<User> {
    override fun search(item: String): List<String> {
        val userNameList: MutableList<String> = mutableListOf()
        for (user in userList) {
            if (user.userName.contains(item)
                || user.name.contains(item)
                || user.surname.contains(item)
            ) {
                userNameList.add(user.userName)
            }
        }

        return userNameList
    }

    override fun printSelectedItem(interestedItem: String) {
        for (selectedUser in userList) {
            if (selectedUser.userName == interestedItem) {
                println("Selected user: id=${selectedUser.id}, username=${selectedUser.userName}, name=${selectedUser.name}, surname=${selectedUser.surname}")
            }
        }
    }

    override fun recent(recentItem: String): List<String> {
        val recentList: MutableList<String> = mutableListOf()
        recentList.add(recentItem)
        return recentList
    }
}

class TagSuggestionController : SuggestionController<Tag> {

    override fun search(item: String): List<String> {
        val hashTagList: MutableList<String> = mutableListOf()
        for (tag in tagList) {
            if (tag.hashTag.toLowerCase().contains(item)) {
                hashTagList.add(tag.hashTag)
            }
        }
        return hashTagList
    }

    override fun printSelectedItem(interestedItem: String) {
        for (selectedTag in tagList) {
            if (selectedTag.hashTag == interestedItem)
                println("Selected hash tag: id=${selectedTag.id}, hash tag=${selectedTag.hashTag}")
        }
    }

    override fun recent(recentItem: String): List<String> {
        val recentList: MutableList<String> = mutableListOf()
        recentList.add(recentItem)
        return recentList
    }
}

fun main(args: Array<String>) {

    println("Please enter username or hash tag")
    println("Note: username should start with @ and hash tags should start with #, else the program will end")

    val searchItem = readLine() ?: return
    val item = if (searchItem != "") {
        searchItem.substring(1, searchItem.length)
    } else return

    if (searchItem.first() == '@') {
        val userSuggestionController: SuggestionController<User> = UserSuggestionController()
        val searchedUsers = userSuggestionController.search(item)

        println("Result: $searchedUsers") // matched users that contain item

        if (searchedUsers.isNotEmpty()) {
            println("Type username that you are interested in")
            val userName = readLine() ?: return
            val recentUsers = userSuggestionController.recent(userName)
            if (searchedUsers.contains(userName)) {
                userSuggestionController.printSelectedItem(userName)
                println("For recent searches type '@',and any other character for esc")
                val recent: String = readLine() ?: return
                if (recent == "@") {
                    println("searched users: $recentUsers")
                }
            } else {
                println("selected username was not found in this list")
            }
        } else {
            println("The list is empty")
        }
    }

    if (searchItem.first() == '#') {
        val tagSuggestionController: SuggestionController<Tag> = TagSuggestionController()
        val searchedTags = tagSuggestionController.search(item)

        println("Result: $searchedTags") // matched tags that contain item
        if (searchedTags.isNotEmpty()) {
            println("Type hash tag that you are interested in")
            val hashTag = readLine() ?: return
            val recentTags = tagSuggestionController.recent(hashTag)
            if (searchedTags.contains(hashTag)) {
                tagSuggestionController.printSelectedItem(hashTag)
                println("For recent searches type '#', and any other character for esc")
                val recent: String = readLine() ?: return
                if (recent == "#") {
                    println("searched users: $recentTags")
                }
            } else {
                println("selected tag was not found in this list")
            }
        } else {
            println("The list is empty")
        }
    }
}