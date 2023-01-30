package com.basic.boards.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Post(
    id: Long,

    @Column(nullable = false)
    var title: String,

    @Lob()
    var contents: String,

    @ManyToOne()
    @JoinColumn(name = "user_id")
    val author: User,

    @OneToMany(mappedBy = "post")
    private val comments: List<Comment>,

    @Column(nullable = false)
    var viewCount: Int = 0

): BaseEntity() {
    fun commentsCount(): Int {
        return this.comments.count()
    }

    fun increaseViewCount() {
        this.viewCount++
    }
}
