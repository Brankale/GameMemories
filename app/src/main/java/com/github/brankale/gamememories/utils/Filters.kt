package com.github.brankale.gamememories.utils

class Filter {

    companion object {
        val DEFAULT = Filter.Builder().build()
    }

    private val field: List<String>

    private constructor(
        field: List<String>
    ) {
        this.field = field
    }

    class Builder {

        private var field = mutableListOf<String>()

        fun addField(fieldName: String): Builder {
            field.add(fieldName)
            return this
        }

        fun build(): Filter {
            return Filter(
                field = if (field.isEmpty()) listOf("*") else field
            )
        }

    }

    override fun toString(): String {
        return buildString {
            constructFields(this)
        }
    }

    private fun constructFields(stringBuilder: StringBuilder) {
        with(stringBuilder) {
            append("fields ")
            field.forEachIndexed { index: Int, fieldName: String ->
                if (index != field.lastIndex)
                    append("$fieldName,")
                else
                    append("$fieldName;")
            }
        }
    }

}