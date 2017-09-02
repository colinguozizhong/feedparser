package com.tuuzed.feedparser

import org.slf4j.LoggerFactory
import java.util.*


class FeedCallbackImpl : FeedCallback {
    private val logger = LoggerFactory.getLogger(javaClass)!!
    override fun start() {
        logger.info("")
    }

    override fun title(title: String, type: String?) {
        logger.info("title: $title, type: $type")
    }

    override fun subtitle(subtitle: String, type: String?) {
        logger.info("subtitle: $subtitle, type: $type")
    }

    override fun link(link: String, type: String?, title: String?) {
        logger.info("link: $link, type: $type, title: $title")
    }

    override fun copyright(copyright: String, type: String?) {
        logger.info("copyright: $copyright, type: $type")
    }

    override fun generator(generator: String, uri: String?, version: String?) {
        logger.info("generator: $generator, uri: $uri, version: $version")
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

    override fun itemStart() {
        logger.info("")
    }

    override fun itemAuthor(author: String, uri: String?, email: String?) {
        logger.info("author: $author, uri: $uri, email: $email")
    }

    override fun itemTitle(title: String) {
        logger.info("title: $title")
    }

    override fun itemId(id: String) {
        logger.info("id: $id")
    }

    override fun itemLink(link: String, type: String?, title: String?) {
        logger.info("link: $link, type: $type, title: $title")
    }

    override fun itemPublished(published: Date?, rawPublished: String) {
        logger.info("published: $published , rawPublished: $rawPublished")
    }

    override fun itemSource(source: String) {
        logger.info("source: $source")
    }

    override fun itemUpdated(updated: Date?, rawUpdated: String) {
        logger.info("updated: $updated, rawUpdated: $rawUpdated")
    }

    override fun itemComments(comments: String) {
        logger.info("comments: $comments")
    }

    override fun itemSummary(summary: String, type: String?, language: String?) {
        logger.info("summary: $summary, type: $type, language: $language")
    }

    override fun itemCategory(category: String, term: String?, scheme: String?) {
        logger.info("category: $category, term: $term, scheme: $scheme")
    }

    override fun itemContent(content: String, type: String?, language: String?) {
        logger.info("type: $type, language: $language, content: $content")
    }

    override fun itemContributor(contributor: String, href: String?, email: String?) {
        logger.info("contributor: $contributor, href: $href, email: $email")
    }

    override fun itemEnclosure(length: String?, type: String?, url: String?) {
        logger.info("length: {$length, type: $length, url: $url")
    }

    override fun itemExpired(expired: Date?, rawExpired: String) {
        logger.info("expired: $expired, rawExpired: $rawExpired")
    }

    override fun itemEnd() {
        logger.info("")
    }
}