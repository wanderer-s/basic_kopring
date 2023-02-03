package com.basic.boards.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Post(
    @Column(nullable = false)
    var title: String,

    @Lob()
    var contents: String,

    @ManyToOne()
    @JoinColumn(name = "user_id")
    val author: User?,

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    val comments: MutableList<Comment?> = mutableListOf(),

    @Column(nullable = false)
    var viewCount: Int = 0

): BaseEntity() {
    fun increaseViewCount() {
        this.viewCount++
    }
}
