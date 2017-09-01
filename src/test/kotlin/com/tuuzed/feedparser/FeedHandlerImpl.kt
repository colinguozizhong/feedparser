package com.tuuzed.feedparser

import org.slf4j.LoggerFactory
import java.util.*


class FeedHandlerImpl : FeedCallback {
    val logger = LoggerFactory.getLogger(javaClass)
    override fun begin() {
        logger.info("")
    }

    override fun title(title: String?) {
        logger.info("title: $title")
    }

    override fun subtitle(subtitle: String?) {
        logger.info("subtitle: $subtitle")
    }

    override fun link(type: String?, href: String?, title: String?) {
        logger.info("type: $type, href: $href, title: $title")
    }

    override fun end() {
        logger.info("")
    }

    override fun error(throwable: Throwable?) {
        logger.info(throwable!!.message, throwable)
    }

    override fun fatalError(throwable: Throwable?) {
        logger.info(throwable!!.message, throwable)
    }

    override fun skipDays(skipDays: List<String>) {
        logger.info("skipDays: $skipDays")
    }

    override fun skipHours(skipHours: List<String>) {
        logger.info("skipHours: $skipHours")
    }

    override fun entryBegin() {
        logger.info("")
    }

    override fun entryAuthor(name: String?, uri: String?, email: String?) {
        logger.info("name: $name, uri: $uri, email: $email")
    }

    override fun entryTitle(title: String?) {
        logger.info("title: $title")
    }

    override fun entryId(id: String?) {
        logger.info("id: $id")
    }

    override fun entryLink(type: String?, href: String?, title: String?) {
        logger.info("type: $type, href: $href, title: $title")
    }

    override fun entryPublished(published: Date?, strPublished: String?) {
        logger.info("published: $published , strPublished: $strPublished")
    }

    override fun entrySource(source: String?) {
        logger.info("source: $source")
    }

    override fun entryUpdated(updated: Date?, strUpdated: String?) {
        logger.info("updated: $updated, strUpdated: $strUpdated")
    }

    override fun entryComments(comments: String?) {
        logger.info("comments: $comments")
    }

    override fun entrySummary(type: String?, language: String?, summary: String?) {
        logger.info("type: $type, language: $language, summary: $summary")
    }

    override fun entryTags(term: String?, scheme: String?, tag: String?) {
        logger.info("term: $term, scheme: $scheme, tag: $tag")
    }

    override fun entryContent(type: String?, language: String?, content: String?) {
        logger.info("type: $type, language: $language, content: $content")
    }

    override fun entryContributor(name: String?, href: String?, email: String?) {
        logger.info("name: $name, href: $href, email: $email")
    }

    override fun entryEnclosure(length: String?, type: String?, url: String?) {
        logger.info("length: {$length, type: $length, url: $url")
    }

    override fun entryExpired(expired: Date?, strExpired: String?) {
        logger.info("expired: $expired, strExpired: $strExpired")
    }

    override fun entryEnd() {
        logger.info("")
    }
}