package dev.remodded.recore.test.utils

import dev.remodded.recore.api.utils.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.util.HSVLike
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TextExtensionsTest {

    @Test
    fun `test text and translatable components creation`() {
        val text = "Hello, world!"
        val component = text.text()
        assertEquals(Component.text("Hello, world!"), component)

        val key = "key.example"
        val translatableComponent = key.translatable()
        assertEquals(Component.translatable("key.example"), translatableComponent)
    }

    @Test
    fun `test legacy text deserialization`() {
        val legacyText = "&6Golden text"
        val component = legacyText.legacyText()
        assertEquals(Component.text("Golden text", NamedTextColor.GOLD), component)
    }

    @Test
    fun `test rgb and hex color applications`() {
        val componentRgb = "Colored".text().rgb(255, 0, 0)
        assertEquals(TextColor.color(255, 0, 0), componentRgb.color())

        val componentHex = "Hex Color".text().hex("#FF5733")
        assertEquals(TextColor.fromHexString("#FF5733"), componentHex.color())
    }

    @Test
    fun `test hsv color application`() {
        val componentHsv = "HSV Color".text().hsv(0.5f, 1.0f, 1.0f)
        val expectedColor = TextColor.color(HSVLike.hsvLike(0.5f, 1.0f, 1.0f))
        assertEquals(expectedColor, componentHsv.color())
    }

    @Test
    fun `test color shortcuts`() {
        assertEquals(NamedTextColor.AQUA, "Aqua".aqua().color())
        assertEquals(NamedTextColor.BLACK, "Black".black().color())
        assertEquals(NamedTextColor.BLUE, "Blue".blue().color())
        assertEquals(NamedTextColor.DARK_AQUA, "Dark Aqua".darkAqua().color())
        assertEquals(NamedTextColor.DARK_BLUE, "Dark Blue".darkBlue().color())
        assertEquals(NamedTextColor.DARK_GRAY, "Dark Gray".darkGray().color())
        assertEquals(NamedTextColor.DARK_GREEN, "Dark Green".darkGreen().color())
        assertEquals(NamedTextColor.DARK_PURPLE, "Dark Purple".darkPurple().color())
        assertEquals(NamedTextColor.DARK_RED, "Dark Red".darkRed().color())
        assertEquals(NamedTextColor.GOLD, "Gold".gold().color())
        assertEquals(NamedTextColor.GRAY, "Gray".gray().color())
        assertEquals(NamedTextColor.GREEN, "Green".green().color())
        assertEquals(NamedTextColor.LIGHT_PURPLE, "Light Purple".lightPurple().color())
        assertEquals(NamedTextColor.RED, "Red".red().color())
        assertEquals(NamedTextColor.YELLOW, "Yellow".yellow().color())
        assertEquals(NamedTextColor.WHITE, "White".white().color())
    }

    @Test
    fun `test text decorations`() {
        val component = "Decorated".text().bold().italic().underlined().strikethrough().obfuscated()
        assertTrue(component.hasDecoration(TextDecoration.BOLD))
        assertTrue(component.hasDecoration(TextDecoration.ITALIC))
        assertTrue(component.hasDecoration(TextDecoration.UNDERLINED))
        assertTrue(component.hasDecoration(TextDecoration.STRIKETHROUGH))
        assertTrue(component.hasDecoration(TextDecoration.OBFUSCATED))
    }

    @Test
    fun `test component appending`() {
        val component1 = "Hello, ".text()
        val component2 = "world!".text()
        val combinedComponent = component1 + component2
        assertEquals(listOf(component2), combinedComponent.children())
    }

    @Test
    fun `test plus assignment operator for components`() {
        val component = "Hello".text()
        component += " world!".text()
        assertEquals(" world!", (component.children().first() as TextComponent).content())
    }

    @Test
    fun `test plus assignment throws on empty component`() {
        val emptyComponent = Component.empty()
        assertFailsWith<UnsupportedOperationException> {
            emptyComponent += "Should fail".text()
        }
    }

    @Test
    fun `test click and hover events`() {
        val clickEvent = ClickEvent.openUrl("https://example.com")
        val hoverEvent = HoverEvent.showText("Hover Text".text())

        val component = "Click and Hover".text().click(clickEvent).hover(hoverEvent.value())
        assertEquals(clickEvent, component.clickEvent())
        assertEquals("Hover Text".text(), component.hoverEvent()?.value())
    }

    @Test
    fun `test reset style`() {
        val styledComponent = "Styled".text().bold().red()
        val resetComponent = styledComponent.reset()
        assertEquals(Component.empty().style(), resetComponent.style())
    }

    @Test
    fun `test string-based color and decoration methods`() {
        val component = "Sample".aqua().bold().italic().underlined().strikethrough().obfuscated()
        assertEquals(NamedTextColor.AQUA, component.color())
        assertTrue(component.hasDecoration(TextDecoration.BOLD))
        assertTrue(component.hasDecoration(TextDecoration.ITALIC))
        assertTrue(component.hasDecoration(TextDecoration.UNDERLINED))
        assertTrue(component.hasDecoration(TextDecoration.STRIKETHROUGH))
        assertTrue(component.hasDecoration(TextDecoration.OBFUSCATED))
    }
}
