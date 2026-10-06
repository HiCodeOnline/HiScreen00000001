package online.hicode.hiscreen00000001.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.hicode.hiscreen00000001.jetpackcompose.ui.theme.HiScreen00000001WithJetpackComposeTheme
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiScreen00000001WithJetpackComposeTheme {
                HomeScreen()
            }
        }
    }
}

data class NavItem(val title: String, val icon: Int)
data class FilterItem(val title: String, val code: String)

data class TabItem(val title: String, val code: String, val icon: Int)

data class EventCardItem(
    val title: String,
    val author: String,
    val address: String,
    val distance: String,
    val date: String,
    val time: String,
    val star: Float,
    val cover: Int,
    val showMap: Boolean,
    val users: Int,
    val logos: List<Int>
)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val backgroundColor = Color.White
    val backdrop = rememberLayerBackdrop {
        drawRect(backgroundColor)
        drawContent()
    }

    var selectedTab by remember { mutableIntStateOf(0) }

    val filterItems = listOf(
        FilterItem("Upcoming", "upcoming"),
        FilterItem("Today", "today"),
        FilterItem("Tomorrow", "tomorrow"),
        FilterItem("Weekend", "weekend")
    )
    val selectedFilterTagIndex = remember { mutableIntStateOf(0) }

    val tabItems = listOf(
        TabItem("All Event", "all_event", R.drawable.icon_tab5),
        TabItem("New Groups", "new_groups", R.drawable.icon_tab4),
        TabItem("Your Groups", "your_groups", R.drawable.icon_tab3),
        TabItem("Social Activities", "social_activities", R.drawable.icon_tab2),
        TabItem("Other Groups", "other_groups", R.drawable.icon_tab1),
    )

    var selectedTabIndex by remember { mutableStateOf(0) }


    val itemsList = remember {  mutableStateListOf(
        EventCardItem(
            title = "26th Anniversary Picnic (Manhattan)",
            author = "Neverwinter Free Parties",
            address = "all_event",
            distance = "1.4 mi",
            date = "SAT.JUL 11",
            time = "12:30 PM EDT",
            star = 4.8f,
            cover = R.drawable.img_demo1,
            showMap = false,
            users = 326,
            logos = listOf(R.drawable.logo_demo1, R.drawable.logo_demo2, R.drawable.logo_demo3)
        ),
        EventCardItem(
            title = "26th Anniversary Picnic (Manhattan)",
            author = "20s 30s Socializing & Language exchange",
            address = "all_event",
            distance = "2.5 mi",
            date = "SAT.JUL 11",
            time = "16:30 PM EDT",
            star = 4.9f,
            cover = R.drawable.img_demo2,
            showMap = true,
            users = 511,
            logos = listOf(R.drawable.logo_demo3, R.drawable.logo_demo1, R.drawable.logo_demo2)
        ),
        EventCardItem(
            title = "26th Anniversary Picnic (Manhattan)",
            author = "20s 30s Socializing & Language exchange",
            address = "all_event",
            distance = "2.5 mi",
            date = "SAT.JUL 11",
            time = "16:30 PM EDT",
            star = 4.9f,
            cover = R.drawable.img_demo3,
            showMap = true,
            users = 511,
            logos = listOf(R.drawable.logo_demo3, R.drawable.logo_demo1, R.drawable.logo_demo2)
        ),
        EventCardItem(
            title = "26th Anniversary Picnic (Manhattan)",
            author = "20s 30s Socializing & Language exchange",
            address = "all_event",
            distance = "2.5 mi",
            date = "SAT.JUL 11",
            time = "16:30 PM EDT",
            star = 4.9f,
            cover = R.drawable.img_demo4,
            showMap = true,
            users = 511,
            logos = listOf(R.drawable.logo_demo3, R.drawable.logo_demo1, R.drawable.logo_demo2)
        ),
        EventCardItem(
            title = "26th Anniversary Picnic (Manhattan)",
            author = "20s 30s Socializing & Language exchange",
            address = "all_event",
            distance = "2.5 mi",
            date = "SAT.JUL 11",
            time = "16:30 PM EDT",
            star = 4.9f,
            cover = R.drawable.img_demo5,
            showMap = true,
            users = 511,
            logos = listOf(R.drawable.logo_demo3, R.drawable.logo_demo1, R.drawable.logo_demo2)
        ),
    )}
    var isLoading by remember { mutableStateOf(false) }
    var noMore by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val buffer = 1

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            totalItemsNumber > 0 && (totalItemsNumber - lastVisibleItemIndex <= buffer)
        }
    }

    val hazeState = remember { HazeState() }

    LaunchedEffect(shouldLoadMore) {
        if (!noMore && shouldLoadMore && !isLoading) {
            isLoading = true

            scope.launch {
                delay(1500.milliseconds)
                val currentSize = itemsList.size
                if (currentSize >= 6) {
                    isLoading = false
                    noMore = true
                } else {
                    val newData = listOf(
                        EventCardItem(
                            title = "26th Anniversary Picnic (Manhattan)",
                            author = "20s 30s Socializing & Language exchange",
                            address = "all_event",
                            distance = "2.5 mi",
                            date = "SAT.JUL 11",
                            time = "16:30 PM EDT",
                            star = 4.9f,
                            cover = R.drawable.img_demo6,
                            showMap = true,
                            users = 511,
                            logos = listOf(R.drawable.logo_demo3, R.drawable.logo_demo1, R.drawable.logo_demo2)
                        ),
                    )
                    itemsList += newData
                    isLoading = false
                    if (itemsList.size >= 6) {
                        noMore = true
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                        ,
                        contentAlignment = Alignment.CenterStart
                    ) {
                        AppleStyleSearchBar(
                            onSearch = {

                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .wrapContentWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_filter),
                                contentDescription = "Filter"
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(modifier = modifier.background(Color.White).padding(innerPadding)) {
            LazyColumn(
                state = listState,
                modifier = modifier
                    .hazeSource(state = hazeState)
                    .layerBackdrop(backdrop)
                ,
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LazyRow(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(
                                    start = 16.dp,
                                    end = 0.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                items(filterItems.size) { index ->
                                    FilterItemTag(
                                        title = filterItems[index].title,
                                        isSelected = selectedFilterTagIndex.intValue == index,
                                        onClick = {
                                            selectedFilterTagIndex.intValue = index
                                        }
                                    )

                                }
                            }
                        }
                    }
                }

                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        CompositionLocalProvider(LocalRippleConfiguration provides null) {
                            PrimaryScrollableTabRow (
                                selectedTabIndex = selectedTabIndex,
                                edgePadding = 16.dp,
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                indicator = {
                                    TabRowDefaults.PrimaryIndicator(
                                        modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                                        width = Dp.Unspecified,
                                        color = Color.Black
                                    )
                                },
                                divider = {
                                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                }
                            ) {
                                tabItems.forEachIndexed { index, item ->
                                    val isSelected = selectedTabIndex == index

                                    Tab(
                                        selected = isSelected,
                                        onClick = { selectedTabIndex = index },
                                        text = {
                                            Text(
                                                text = item.title,
                                                fontSize = 14.sp,
                                                color = if (isSelected) Color.Black else Color(0xFF8E8E93)
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                painter = painterResource(item.icon),
                                                contentDescription = item.title,
                                                modifier = Modifier.size(22.dp),
                                                tint = Color.Unspecified
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                items(itemsList) { item ->
                    EventCard(
                        event = item
                    )
                }

                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 96.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(36.dp),
                                color = Color(0xFF7561d3),
                                strokeWidth = 3.dp
                            )
                        }
                    }
                }
                if (noMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 96.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No more event",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                containerColor = Color.Transparent
            ) {
                AppleStyleBottomBar(
                    modifier = Modifier,
                    hazeState = hazeState,
                    backdrop = backdrop,
                    currentSelect = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    }
}

@Composable
fun EventCard(event: EventCardItem) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = event.cover),
            contentDescription = "cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        Text(
            text = event.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Row() {
            Text(
                text = event.date,
                fontSize = 13.sp,
            )
            Text(
                text = "·",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 2.dp, end = 2.dp)
            )
            Text(
                text = event.time,
                fontSize = 13.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.distance,
                fontSize = 12.sp,
            )
            Text(
                text = "·",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 2.dp, end = 2.dp)
            )
            Text(
                text = "by",
                fontSize = 12.sp,
            )
            Text(
                text = event.author,
                fontSize = 12.sp,
                modifier = Modifier
                    .weight(1f, fill = false),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "·",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 2.dp, end = 2.dp)
            )
            Text(
                text = "${event.star}",
                fontSize = 12.sp,
            )
            Icon(
                painter = painterResource(R.drawable.icon_star_fill),
                contentDescription = "star",
                modifier = Modifier.size(14.dp),
                tint = Color(0xFFd73967)
            )
        }
    }
}

@Composable
fun FilterItemTag(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 3.dp)
            .then(
                if (isSelected) {
                    Modifier.shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(18.dp),
                        clip = false
                    )
                } else {
                    Modifier
                }
            )
            .background(if (isSelected) Color(0xFF7561d3) else Color.White, RoundedCornerShape(18.dp))
            .then(
                Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clickable(onClick = onClick)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = title,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AppleStyleSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search events or groups...",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = text,
        onValueChange = { text = it },
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp),
        textStyle = TextStyle(
            color = Color(0xFF1C1C1E),
            fontSize = 15.sp
        ),
        cursorBrush = SolidColor(Color(0xFF007AFF)),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
                focusManager.clearFocus()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color(0xFFFEFEFE),
                        spotColor = Color.Black
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_search),
                    contentDescription = "Search Icon",
                    tint = Color(0xFF9C9C9C).copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0xFF9C9C9C).copy(alpha = 0.6f),
                            fontSize = 15.sp
                        )
                    }
                    innerTextField()
                }

                AnimatedVisibility(
                    visible = text.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_close),
                        contentDescription = "Clear Text",
                        tint = Color(0xFF9C9C9C).copy(alpha = 0.6f),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                text = ""
                            }
                    )
                }
            }
        }
    )
}

@Composable
fun AppleStyleBottomBar(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    backdrop: LayerBackdrop,
    currentSelect: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        NavItem("Home", R.drawable.icon_home),
//        NavItem("Friends", R.drawable.icon_user),
        NavItem("Explore", R.drawable.icon_search),
//        NavItem("Notifications", R.drawable.icon_notification),
        NavItem("Messages", R.drawable.icon_chat)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { RoundedCornerShape(36.dp) },
                    effects = {
                        vibrancy()
                        blur(1f.dp.toPx())
                        lens(16f.dp.toPx(), 32f.dp.toPx())
                    },
                    onDrawSurface = { drawRect(Color.Black.copy(alpha = 0.1f)) }
                )
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 128.dp,
                    shape = RoundedCornerShape(36.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(36.dp))
                .hazeEffect(state = hazeState) {
                    blurRadius = 12.dp
                    backgroundColor = Color.White.copy(alpha = 0.9f)
                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { RoundedCornerShape(36.dp) },
                    effects = {
                        vibrancy()
                        blur(1f.dp.toPx())
                        lens(16f.dp.toPx(), 32f.dp.toPx())
                    },
                    onDrawSurface = { drawRect(Color.Black.copy(alpha = 0.1f)) }
                )
                .shadow(
                    elevation = 128.dp,
                    shape = RoundedCornerShape(36.dp),
                    clip = false
                )
                .hazeEffect(state = hazeState) {
                    blurRadius = 12.dp
                    backgroundColor = Color.White.copy(alpha = 0.9f)
                }
                .padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = currentSelect == index

                val tintColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF7561d3) else Color.Black,
                    label = "TintColor"
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onTabSelected(index)
                        }
                        .then(
                            if (isSelected) Modifier.clip(RoundedCornerShape(36.dp)).hazeEffect(state = hazeState) {
                                blurRadius = 12.dp
                                backgroundColor = Color.Black.copy(alpha = 0.2f)
                            } else Modifier
                        )
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title,
                        tint = tintColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = item.title,
                        color = tintColor,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PagePreview() {
    HiScreen00000001WithJetpackComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            HomeScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}