package com.tuuzed.feedparser

import com.tuuzed.feedparser.util.LoggerFactory
import java.util.*


class FeedHandlerImpl : FeedHandler {
    val logger = LoggerFactory.getLogger(javaClass)
    override fun begin() {
        println("begin")
    }

    override fun title(title: String?) {
        println("title: $title")
    }

    override fun subtitle(subtitle: String?) {
        println("subtitle: $subtitle")
    }

    override fun link(type: String?, href: String?, title: String?) {
        println("type: $type, href: $href, title: $title")
    }

    override fun end() {
        println("end")
    }

    override fun error(throwable: Throwable?) {
        logger.info(throwable!!.message, throwable)
    }

    override fun fatalError(throwable: Throwable?) {
        logger.info(throwable!!.message, throwable)
    }

    override fun skipDays(skipDays: List<String>) {
        println("skipDays: $skipDays")
    }

    override fun skipHours(skipHours: List<String>) {
        println("skipHours: $skipHours")
    }

    override fun entryBegin() {
        println("\nentryBegin\n")
    }

    override fun entryAuthor(name: String?, uri: String?, email: String?) {
        println("name: $name, uri: $uri, email: $email")
    }

    override fun entryTitle(title: String?) {
        println("title: $title")
    }

    override fun entryId(id: String?) {
        println("id: $id")
    }

    override fun entryLink(type: String?, href: String?, title: String?) {
        println("type: $type, href: $href, title: $title")
    }

    override fun entryPublished(published: Date?, strPublished: String?) {
        println("published: $published , strPublished: $strPublished")
    }

    override fun entrySource(source: String?) {
        println("source: $source")
    }

    override fun entryUpdated(updated: Date?, strUpdated: String?) {
        println("updated: $updated, strUpdated: $strUpdated")
    }

    override fun entryComments(comments: String?) {
        println("comments: $comments")
    }

    override fun entrySummary(type: String?, language: String?, summary: String?) {
        println("type: $type, language: $language, summary: $summary")
    }

    override fun entryTags(term: String?, scheme: String?, tag: String?) {
        println("term: $term, scheme: $scheme, tag: $tag")
    }

    override fun entryContent(type: String?, language: String?, content: String?) {
        println("type: $type, language: $language, content: $content")
    }

    override fun entryContributor(name: String?, href: String?, email: String?) {
        println("name: $name, href: $href, email: $email")

    }

    override fun entryEnclosure(length: String?, type: String?, url: String?) {
        println("length: {$length, type: $length, url: $url")
    }

    override fun entryExpired(expired: Date?, strExpired: String?) {
        println("expired: $expired, strExpired: $strExpired")
    }

    override fun entryEnd() {
        println("\nentryEnd\n")
    }
}