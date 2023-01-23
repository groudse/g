import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.finalapp.Games
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.RoundingMode
import java.text.DecimalFormat

object req {

    val gson = Gson()

    fun exchange(from: String, to: String, q: Double): Double {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://currency-exchange.p.rapidapi.com/exchange?from=${from}&to=${to}&q=${q}")
            .get()
            .addHeader("X-RapidAPI-Key", "5b548fb8c3msh5df131ddc5ddc04p1be736jsn77517db2efd0")
            .addHeader("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
            .build()

        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body!!.string().toDouble()
        }


    }

    fun discount(gameName: String): String {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://cheapshark-game-deals.p.rapidapi.com/games?title=${gameName}&limit=200")
            .get()
            .addHeader("X-RapidAPI-Key", "5b548fb8c3msh5df131ddc5ddc04p1be736jsn77517db2efd0")
            .addHeader("X-RapidAPI-Host", "cheapshark-game-deals.p.rapidapi.com")
            .build()

        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body!!.string()
        }
    }

    fun loadGames(gameName: String): Games {


        val json: String = discount(gameName)
        val data = gson.fromJson(json, Games::class.java)

        return data

    }


    fun ret(quantity: Double, value: Double): Double {
        return value * quantity
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

    fun isOnline(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }


}