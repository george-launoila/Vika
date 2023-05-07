package com.example.george

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.george.databinding.FragmentCommentDetailBinding
import com.example.george.databinding.FragmentDataReadBinding


class CommentDetailFragment : Fragment() {
    private var _binding: FragmentCommentDetailBinding? = null

    val args: CommentDetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.d("TESTI", "id:" + args.id.toString())

        // Rakennetaan id-argumentin avulla uusi JSON_URL, VOLLEYTÄ VARTEN
        // toisin sanoen, URL viittaa yksittäiseen kommenttiin rajapinnassa id:n perusteella
        val JSON_URL = "https://jsonplaceholder.typicode.com/comments"
        Log.d("TESTI", JSON_URL)

        // Pääasiassa kolme eri lähestymistapaa, miten jatkaa tästä:

        // VAIHTOEHTO 1: ota vastaan pelkkä klikattu id (kuten tässä nyt), ja hae sitä vastaava
        // data Volleylla uudestaan.
        // var item : Comment = gson.fromJson(response, Comment::class, java)
        // binding.jokutextView.text = item.name.toString()
        // aina ajantasainen data, enemmän koodia/enemmän tietoliikennettä

        // VAIHTOEHTO 2: lähetä edellisestä fragmentista lisää argumentteja, eli kaikki muutkin
        // yhden kommentin tiedot (nimi, email, body, jne.)
        // helppo koodata/vähän koodia, data saattaa olla vanhentunut

        // VAIHTOEHTO 3: lähetä edellisestä fragmentista koko comment-olio JSON-formaatissa (käytä JSONia)
        // tässä detail-fragmentissa, pura JSON takaisin Kotlin-olioksi
        // suurikaan määrä argumentteja ei haittaa, data saattaa olla vanhentunut

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}