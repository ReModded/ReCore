package dev.remodded.recore.api.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.util.HSVLike


fun String.text() = Component.text(this)
fun String.translatable() = Component.translatable(this)

operator fun Component.plus(other: Component) = this.append(other)
operator fun Component.plus(other: String) = this + other.text()


fun Component.rgb(r: Int, g: Int, b: Int) = this.color(TextColor.color(r, g, b))
fun Component.hex(hex: String) = this.color(TextColor.fromHexString(hex))
fun Component.hex(hex: Int) = this.color(TextColor.color(hex))
fun Component.hsv(h: Float, s: Float, v: Float) = this.color(TextColor.color(HSVLike.hsvLike(h, s, v)))

fun Component.reset() = this.style(Style.empty())

fun Component.aqua() = this.color(NamedTextColor.AQUA)
fun Component.black() = this.color(NamedTextColor.BLACK)
fun Component.blue() = this.color(NamedTextColor.BLUE)
fun Component.darkAqua() = this.color(NamedTextColor.DARK_AQUA)
fun Component.darkBlue() = this.color(NamedTextColor.DARK_BLUE)
fun Component.darkGray() = this.color(NamedTextColor.DARK_GRAY)
fun Component.darkGreen() = this.color(NamedTextColor.DARK_GREEN)
fun Component.darkPurple() = this.color(NamedTextColor.DARK_PURPLE)
fun Component.darkRed() = this.color(NamedTextColor.DARK_RED)
fun Component.gold() = this.color(NamedTextColor.GOLD)
fun Component.gray() = this.color(NamedTextColor.GRAY)
fun Component.green() = this.color(NamedTextColor.GREEN)
fun Component.lightPurple() = this.color(NamedTextColor.LIGHT_PURPLE)
fun Component.red() = this.color(NamedTextColor.RED)
fun Component.yellow() = this.color(NamedTextColor.YELLOW)
fun Component.white() = this.color(NamedTextColor.WHITE)

fun Component.obfuscated() = this.decorate(TextDecoration.OBFUSCATED)
fun Component.bold() = this.decorate(TextDecoration.BOLD)
fun Component.strikethrough() = this.decorate(TextDecoration.STRIKETHROUGH)
fun Component.underlined() = this.decorate(TextDecoration.UNDERLINED)
fun Component.italic() = this.decorate(TextDecoration.ITALIC)

fun Component.click(action: ClickEvent) = this.clickEvent(action)
fun Component.hover(action: Component) = this.hoverEvent(action)
fun Component.hover(action: String) = this.hoverEvent(action.text())


fun String.rgb(r: Int, g: Int, b: Int) = this.text().rgb(r, g, b)
fun String.hex(hex: String) = this.text().hex(hex)
fun String.hex(hex: Int) = this.text().hex(hex)
fun String.hsv(h: Float, s: Float, v: Float) = this.text().hsv(h, s, v)

fun String.aqua() = this.text().aqua()
fun String.black() = this.text().black()
fun String.blue() = this.text().blue()
fun String.darkAqua() = this.text().darkAqua()
fun String.darkBlue() = this.text().darkBlue()
fun String.darkGray() = this.text().darkGray()
fun String.darkGreen() = this.text().darkGreen()
fun String.darkPurple() = this.text().darkPurple()
fun String.darkRed() = this.text().darkRed()
fun String.gold() = this.text().gold()
fun String.gray() = this.text().gray()
fun String.green() = this.text().green()
fun String.lightPurple() = this.text().lightPurple()
fun String.red() = this.text().red()
fun String.yellow() = this.text().yellow()
fun String.white() = this.text().white()

fun String.obfuscated() = this.text().obfuscated()
fun String.bold() = this.text().bold()
fun String.strikethrough() = this.text().strikethrough()
fun String.underlined() = this.text().underlined()
fun String.italic() = this.text().italic()

fun String.click(action: ClickEvent) = this.text().click(action)
fun String.hover(action: Component) = this.text().hover(action)
fun String.hover(action: String) = this.text().hover(action.text())
