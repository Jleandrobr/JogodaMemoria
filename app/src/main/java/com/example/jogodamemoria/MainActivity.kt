package com.example.jogodamemoria

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var cardAdapter: CardAdapter
    private val cards = mutableListOf<Card>()
    private var firstCard: Card? = null
    private var secondCard: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        cardAdapter = CardAdapter(this, cards)
        gridView.adapter = cardAdapter

        // Crie pares de cartas e adicione-os à lista de cartas
        val cardIds = listOf(
            R.drawable.gustavo, R.drawable.gustavo,
            R.drawable.fred, R.drawable.fred,
            R.drawable.card3, R.drawable.card3,
            R.drawable.card4, R.drawable.card4,
            R.drawable.card5, R.drawable.card5,
            R.drawable.card6, R.drawable.card6
        )

        cardIds.shuffle()
        for (id in cardIds) {
            cards.add(Card(id))
        }

        gridView.setOnItemClickListener { _, view, position, _ ->
            val card = cards[position]
            if (!card.isFaceUp) {
                revealCard(card, view)
                if (firstCard == null) {
                    firstCard = card
                } else {
                    secondCard = card
                    checkForMatch()
                }
            }
        }
    }

    private fun revealCard(card: Card, view: View) {
        card.isFaceUp = true
        cardAdapter.notifyDataSetChanged()
        view.isClickable = false
    }

    private fun hideCards() {
        firstCard?.isFaceUp = false
        secondCard?.isFaceUp = false
        cardAdapter.notifyDataSetChanged()
        firstCard = null
        secondCard = null
    }

    private fun checkForMatch() {
        if (firstCard?.id == secondCard?.id) {
            firstCard = null
            secondCard = null
        } else {
            val handler = Handler()
            handler.postDelayed({
                hideCards()
            }, 1000)
        }
    }
}
