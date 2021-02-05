package com.codingwithme.multiviewrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithme.multiviewrecyclerview.adapter.NewsFeedAdapter
import com.codingwithme.multiviewrecyclerview.model.NewsFeedModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var nameEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var logoutButton : Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        db = FirebaseDatabase.getInstance().getReference("UserInfo")
        mAuth = FirebaseAuth.getInstance()

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        nameEditText = findViewById(R.id.nameEditText)
        nameTextView = findViewById(R.id.nameTextView)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val personInfo = PersonInfo(name)
            if (mAuth.currentUser?.uid != null) {
                db.child(mAuth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                        nameEditText.text = null
                    }else {
                        makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            if (mAuth.currentUser?.uid != null) {
                db.child(mAuth.currentUser?.uid!!)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            makeText(this@UserActivity, "error", Toast.LENGTH_SHORT).show()
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val p = snapshot.getValue(PersonInfo::class.java)
                            if (p != null) {
                                nameTextView.text = p.name
                            }

                        }


                    })
            }

        }

        val newsFeedArray = ArrayList<NewsFeedModel>()
        newsFeedArray.add(NewsFeedModel("https://i.pinimg.com/originals/04/c7/8a/04c78a3bec46babab4a23e3e13091552.jpg","კაციტა","16 mins",3,"გაშალა ფარშევანგმა კუდი","","https://r5---sn-4g5e6nls.googlevideo.com/videoplayback?expire=1612522067&ei=880cYOuNIY3vgAeb_a-gAg&ip=77.82.235.247&id=o-ADoXAaYs23iojb-2UNu49adIViAAm5KGELU0sbxVtWfy&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=_hIzJcHAcF3ZNwzWG5FcOIsF&gir=yes&clen=23452616&ratebypass=yes&dur=250.798&lmt=1572471090781646&fvip=5&c=WEB&txp=5431432&n=cZndvEWzokHX7w0c6&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAOyS6tSc8S2sbVMC_Yie4dXRp7KxMN1QqDaZncLw8dmtAiBGL9URAng5MROIAzSPOJiMZ_aJ6bmHO6mwfvS_CTGuxQ%3D%3D&rm=sn-gvnuxaxjvh-23je7e,sn-n8vyl76&req_id=e57c9d87def7a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mh=az&mip=94.43.181.79&mm=29&mn=sn-4g5e6nls&ms=rdu&mt=1612500279&mv=m&mvi=5&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgZQOZnUW8WzqlAZ7lHE1IEhFCDsP8qT306wp_xhKkz0kCIQDef3aW6IeFCMFsbZqaHG9MbjD4xN4041pBgxtK9O8kYg%3D%3D"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/136663740_4001394923288133_8553091454593312198_n.jpg?_nc_cat=110&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeED_KhAHVRl1IM539kAi88b3G_KSXX7Tovcb8pJdftOiwWQcMzm5mfiNABm_opNt5nZKf_HzzT1G6csXTFL4a3x&_nc_ohc=2zX9UouItP8AX82ezh5&_nc_ht=scontent.ftbs6-1.fna&oh=b6d5158ddee0d08cff275a38ee634f28&oe=6043FD54","სალაძე","48 mins",3,"ასე ძალიან ვუყვარვარ ნიკოლოზს","","https://r6---sn-4g5e6ne6.googlevideo.com/videoplayback?expire=1612517420&ei=zLscYOOPK4rPxwKt-Z3QCw&ip=183.88.5.98&id=o-AJAWHhELh4Jwl0vi3OFmr99Yo4Np7ID19z1Pmq16ZaXs&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=uT5WgwL-P0pXwO47GIpP8F8F&gir=yes&clen=107456&ratebypass=yes&dur=3.529&lmt=1612495732453860&fvip=6&c=WEB&txp=6200222&n=5i_sCxQspkgHCQYtZ&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgTrjg0ipKUyngtxH1PVrBgpKJAAX79HJosQZ0HGmdetQCIG0RF7FrkgilpfX7bo9ZF5cjpLNxtGbBp9kV20VWS4Gq&rm=sn-w5nuxa-o53s7d,sn-w5nuxa-c33ek7d,sn-30aer7s&req_id=7904566cf9c1a3ee&ipbypass=yes&redirect_counter=4&cm2rm=sn-4g5elz7d&cms_redirect=yes&mh=eU&mip=94.43.181.79&mm=34&mn=sn-4g5e6ne6&ms=ltu&mt=1612495711&mv=m&mvi=6&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIhAJz9m-owEqi8sy9w2xP_9X1LcxU_yCU3EDBqzVbtNV7cAiAbWfw3PdFhNSpBlvzqp2vuDvv2ERHpUnH7DrW6lOEPOQ%3D%3D"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/116317117_2023122871153454_2760508135382457768_o.jpg?_nc_cat=105&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeG9r6qXTMYlz2EOR5NddB6MhII3OHiXW0WEgjc4eJdbRXt6qBkreekjbtpuFtIohfrnbza_C3_qCku5uPSMtsEW&_nc_ohc=ReiRMvBLBxYAX_nTjE6&_nc_ht=scontent.ftbs6-1.fna&oh=a8f5f394c6164758faed3d643e01c83f&oe=60413EBE","ანო ","48 mins",3,"","","https://r2---sn-4g5e6nlk.googlevideo.com/videoplayback?expire=1612518705&ei=0cAcYMCuK_-dzLUP582A6AQ&ip=209.107.204.62&id=o-ADUfu9_J-_M54KMtkIQuNjSTfZrLxt4mYolkWdPia4KA&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=boSSeEqEyAY3SbTpgrMnuc8F&gir=yes&clen=172679&ratebypass=yes&dur=6.431&lmt=1612497023080926&fvip=2&c=WEB&txp=6200222&n=p4BIDKNeMrshN5ckw&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgM8ZYR4P79BSCHvvXkiOVSugt9McbJ8D6Ym1UdA82a9ICIQDUGiXvmYkT9XxWp1MWTPBqLOXcQCxLBNQMqEVjvoffeQ%3D%3D&rm=sn-hp5r77l&req_id=d1ba36f4d80a3ee&ipbypass=yes&cm2rm=sn-npa3oxu-ucns7l,sn-4g5e6k7l&redirect_counter=3&cms_redirect=yes&mh=mJ&mip=94.43.181.79&mm=34&mn=sn-4g5e6nlk&ms=ltu&mt=1612496915&mv=m&mvi=2&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAJKQemk4XyhR58ChObmPDdoJVqD1jrYSABPOyvqFma-qAiEAplVn_JAum52iIdjRkGXBgTODpujA0gYEGBKDAu67NFM%3D"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/71202627_2379470128942752_8410666376506638336_n.jpg?_nc_cat=110&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeG-pev8j-62igIkPLN3wyEWfKwfcz8Td618rB9zPxN3rWPQFb8dIpShJJgiBbcGZCB6E0D4AL2WtFMK35i-Tmgv&_nc_ohc=lDfwNwETpdoAX-iFeBL&_nc_ht=scontent.ftbs6-1.fna&oh=aeff221da56e336eebb60aa40b2453e6&oe=60411715","ირაკლი მღვდლიაშვილი","8 mins",2,"კაციტა:რა გაიგეთ მეგობრებო?ჩვენ ამ დროს","https://cdn.statically.io/img/www.musicnotes.com/now/wp-content/uploads/Best_Memes_2018.jpg?quality=80&f=auto&fbclid=IwAR3tX64Vrgi5w-AIuuBLpkJJKfyBLesVrKMOsiSOw7zftuHxX597y6b1v1E",""))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/101350597_3409922635702048_1365244952346361856_n.jpg?_nc_cat=101&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeEQDHkNl-0cnoXLKMUtAq5NiLmgTvZiRdyIuaBO9mJF3Ag_zFSyUvmkSCr0-EGe8pG_zZIIWUTOnhskCxPEBr89&_nc_ohc=D563PveZbnEAX-DlnD3&_nc_ht=scontent.ftbs6-1.fna&oh=ff211d76f332d04a509f95d40c30fad1&oe=60410A87","ქეთათო","48 mins",3,"","","https://r1---sn-q4flrn7y.googlevideo.com/videoplayback?expire=1612519160&ei=l8IcYODLO9Wb2_gP9NSr2Ao&ip=209.107.216.79&id=o-AMgjr-4USPkLPFS3kF5FcdtVjoFKOMhkwa6XeMFRZrfm&itag=22&source=youtube&requiressl=yes&mh=cR&mm=31%2C26&mn=sn-q4flrn7y%2Csn-a5mlrn7l&ms=au%2Conr&mv=m&mvi=1&pl=24&initcwndbps=1113750&vprv=1&mime=video%2Fmp4&ns=SZhpwT-9wqbwLDKD83dYSyEF&ratebypass=yes&dur=73.049&lmt=1601026237820134&mt=1612497169&fvip=1&c=WEB&txp=5432434&n=REPXbCzktASSjER7j&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgH52NBFh0mlE9zO3d3oE-IIT_2olAlgFXytiFh8NXUiYCIQCMMF_D5aOJIEVWm6J1j0pkpLQV471T8qaidRZ322FbuQ%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRgIhANLInELURYdNp-9pmW45r886bophZbiUUiEYzt9rC616AiEA1ny3o04cevAcAGCX3PeYC2sfD09WbhW2tr--jL6EiV0%3D&title=Mafia%3A%20Definitive%20Edition%20-%20Launch%20Trailer%20%22When%20All%20is%20Not%20What%20it%20Seems%22"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/118483800_1808196556021962_4838758598533049936_o.jpg?_nc_cat=105&ccb=2&_nc_sid=e3f864&_nc_eui2=AeEm84vNAwmrjnVJPc0OZvdaDDS_54hZyfwMNL_niFnJ_AFMPOl7AaHk0tvhErn9HRrhbz9ytdyYlM6WpKJmyivM&_nc_ohc=AwoumxmABFsAX-o9asV&_nc_ht=scontent.ftbs6-1.fna&oh=126133db58dc4ddc66b10d11ef61ffa1&oe=60438378","ლუკა ლაზარაშვილი","48 mins",3,"","","https://r1---sn-qxa7sn7l.googlevideo.com/videoplayback?expire=1612521937&ei=cc0cYLieC5D9xwLCnaToCQ&ip=103.47.219.117&id=o-AKwMogUtMw5528R30hi9gbEqFXDP0Ps2kv8sdLg4sZkt&itag=22&source=youtube&requiressl=yes&mh=Rl&mm=31%2C26&mn=sn-qxa7sn7l%2Csn-cvh7knek&ms=au%2Conr&mv=m&mvi=1&pl=24&pcm2=yes&initcwndbps=235000&vprv=1&mime=video%2Fmp4&ns=0qRyxLKWW-yxBN40vbn3J5gF&ratebypass=yes&dur=7.314&lmt=1612500105068984&mt=1612500039&fvip=1&c=WEB&txp=6216222&n=mUTBmBgEgbNl3AaTW&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cpcm2%2Cvprv%2Cmime%2Cns%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgVKNukqxm69BLNqmExgHmWaROJM8eK3GaUg-fO0LsnhECIC9t0AHfY-Oldqsf3KgmlLkMkBXqd9ZW8VZMgw7C86SB&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgMXffQ38DsuaufGZvmcL0T-eTwZaPmNK4aMDWKRHR-SECICSaWVgE23Nau79YwN3Do6LGzQNUSumRzz39aX_7JXuy&title=5%20%E1%83%97%E1%83%94%E1%83%91%E1%83%94%E1%83%A0%E1%83%95%E1%83%90%E1%83%9A%E1%83%98%2C%202021"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/67882093_2109874052648064_2706188356961697792_n.jpg?_nc_cat=109&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeEZ_ASckRBf2NfHpO8fhQ1aZeKyj7fR7Gpl4rKPt9HsanFjGHpjzmfzm3BHEZlw3x-02z3RJx8TTO0SbYxNOXgh&_nc_ohc=sCH4Uciw-ywAX8nSqfv&_nc_ht=scontent.ftbs6-1.fna&oh=537ed5535c97d230cba0cb4f2611ea9b&oe=6043AC21","ანა ლომიძე","18 mins",3," დააგენერირეთ he is My fav lector <3","","https://r1---sn-ab5szn7y.googlevideo.com/videoplayback?expire=1612517104&ei=kLocYPKqHtaYir4PlMCc-As&ip=45.55.46.246&id=o-AH8QoAFCkLG--7gmTKWMcdQ0pzi8O8R2N6tCN15l-Rr1&itag=22&source=youtube&requiressl=yes&mh=iy&mm=31%2C29&mn=sn-ab5szn7y%2Csn-ab5l6n6l&ms=au%2Crdu&mv=m&mvi=1&pl=23&initcwndbps=188750&vprv=1&mime=video%2Fmp4&ns=2xa4k2ER4Nt6kwi8iKYGu8MF&cnr=14&ratebypass=yes&dur=32.786&lmt=1612495316233495&mt=1612495004&fvip=1&c=WEB&txp=6211222&n=674Wy3gzHsgJOql4S&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgGgV1b34hRXTMm0AhaT2KJIsAfOf6k4kFLZ8biqMnKUsCIBVKIioM_Smh8eIGIdn1SpmVrnRJ5jLqgba_etKTzgkj&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIgbJ4zyFMtPcHg_DR3SBRBBu-QBOsDd3moro95zSOKSSMCIQC9nvbe-KhoW2Cq-u1f5ONW_oi3BB7ajMMQbFH4MZ4rxw%3D%3D&title=%E1%83%92%E1%83%90%E1%83%A8%E1%83%90%E1%83%9A%E1%83%90%E1%83%90%20%E1%83%92%E1%83%90%E1%83%A8%E1%83%90%E1%83%9A%E1%83%90"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/127225684_1704010289764553_2444083304106456092_o.jpg?_nc_cat=102&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeFtmeRp9TCjecDZvUTqnu3h9evG1-5Wruv168bX7lau662_JnndIWtEjy5HklV1cqA4sscD2R27_YK5SOYzvuPd&_nc_ohc=nGzidIlONFUAX_ZnX1O&_nc_oc=AQnH8Jv5h3q7oxFeSoZ_z2bZy4TCR-dBid697Z8uDg6Yw71IMkTGH387YKPbWbSbTQI&_nc_ht=scontent.ftbs6-1.fna&oh=fc110b75e269286a80e05779af961f9d&oe=6042D000","ჟიჟო","48 mins",3,"","","https://r2---sn-4g5edns6.googlevideo.com/videoplayback?expire=1612518078&ei=Xr4cYKce0O-KBPC0kZAM&ip=207.244.239.235&id=o-ADyK9oUw8Qk6rPA6fhsL0Ut23T08qNyeW7dRuyp_r3Vz&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=3tOl4l1wqCM7_75GbtraYFsF&gir=yes&clen=116038092&ratebypass=yes&dur=3609.123&lmt=1604592184137900&fvip=2&beids=9466587&c=WEB&txp=5531432&n=U2wixJ6kpjEhOk2fE&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAOCMtP2FrUdG7cFTHPr7UfiS9BtUg23T1TXYxqRZPOktAiA_pEI_KsqUrvTuYOp4Yd3N0xeePH3iQ7dkbC9VUPgQKg%3D%3D&rm=sn-ca0xcpgq-nual7l,sn-vgqe777l&fexp=9466587&req_id=dff7bf4d76f9a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mh=Jt&mip=94.43.181.79&mm=29&mn=sn-4g5edns6&ms=rdu&mt=1612496450&mv=m&mvi=2&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgIzkJji5rNV2UJyWlboloRD9brrxbx202f8Xi20g5P_0CIQDHSEiK5cijsgJPyM82MZPTnG0XOR9Ih86lpFX4v8Re1w%3D%3D"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/78342166_1688779114613359_3872260660586872832_o.jpg?_nc_cat=106&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeE1XdbbRxgLDVQHJ3FpzQ6lBkFv_vSuQhsGQW_-9K5CG1KHZ3EOwSv_4FFcX-YssAoef676h7S3h5ct1CngN5GE&_nc_ohc=ihUBjWpzebMAX-cFK08&_nc_ht=scontent.ftbs6-1.fna&oh=c4d9472d8a518931c7e8be8304ce8ef3&oe=6043A4AA","გიორგი ლასურაშვილი","48 mins",3,"ლა ვუკო ლაა","","https://r5---sn-4g5ednss.googlevideo.com/videoplayback?expire=1612520521&ei=6cccYNiHDdyu7gKMnrWYAg&ip=181.196.142.114&id=o-ALZ3ym-_cMqffgOdc_Y6EejFPRjEj5NPO4hTpR2cx44C&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=pRUQg9FMXji8IVllRGq9CscF&gir=yes&clen=78522&ratebypass=yes&dur=1.555&lmt=1395095066439820&fvip=5&c=WEB&n=GykfSBWeY4uip7RBY&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAKhQV6mV1cMFqwOB5w6_0mSXlovS42wx0KXSeS5NmGMaAiANm5rHtJNum_0GpM9LlPrKMfdZ1al-sRLZWDRIyKf_1Q%3D%3D&rm=sn-jou-btxy7e,sn-jou-btxl76&req_id=c966b5f0c359a3ee&redirect_counter=3&cm2rm=sn-hp5rd76&cms_redirect=yes&mh=nA&mip=94.43.181.79&mm=34&mn=sn-4g5ednss&ms=ltu&mt=1612498778&mv=u&mvi=5&pl=24&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgVxlZFPTtrXUAdn04JS0UFCTkMGI9NDyHE0w8gfxwd6ACIB8whqtP4l_wWVi5M_ZU6W90eOksIW9Tf1N_2Fddio_w"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/87752055_2786544921426427_4038034617117179904_n.jpg?_nc_cat=107&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeFFSPKcoZ94uJ9nYUFAo3oxeKTUnMTW9Hh4pNScxNb0eE7owFDcUwwBgLV1ZfYbvRf8uQDjrb-420iUgfl7h4aO&_nc_ohc=b6HS3AAx8gkAX_zks71&_nc_ht=scontent.ftbs6-1.fna&oh=8b559d65ab88ca5f9a75b55377fde63d&oe=60412E1E","ნიკოლოზ კვერნაძე","48 mins",3,"","","https://r4---sn-5ouxa-h8q6.googlevideo.com/videoplayback?expire=1612521191&ei=hsocYJ3aO4-IWJDkodAC&ip=190.64.131.250&id=o-ADnrAfsUGs4kvEW20vGIzP8pP28j7bnvJS5a_y9NTdfV&itag=22&source=youtube&requiressl=yes&mh=S9&mm=31%2C29&mn=sn-5ouxa-h8q6%2Csn-x1x7zn7e&ms=au%2Crdu&mv=m&mvi=4&pcm2cms=yes&pl=24&initcwndbps=663750&vprv=1&mime=video%2Fmp4&ns=1nv1MKgyK2fqbbei8U5lYMcF&cnr=14&ratebypass=yes&dur=2.972&lmt=1489948026038686&mt=1612499320&fvip=4&c=WEB&n=HR7J33_TCvgsLPqTo&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhANaeku_9gfxJRqalzQHMZSJqaVy5bIwMvLmAP9VniUBWAiEAvOQAebqz-90dPsg5RaDtOrq1KYf827Q4v8z9Z7r8UTA%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAO1TOnTHS6tbOsZVspvPK6Jbpj2WRDdw47r-bHVdbcmhAiBrl1WCEqI4sNrK_GCKOH3pubSjTM5_pOZx3Q9dZfz48A%3D%3D&title=%E1%83%92%E1%83%90%E1%83%95%E1%83%92%E1%83%98%E1%83%9F%E1%83%93%E1%83%94%E1%83%91%E1%83%98..!"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/72844775_2523332157763802_8107365192041496576_o.jpg?_nc_cat=108&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeFuZ-vMCU2M9aQlJj-Yh3RFeIvKsnKK7s54i8qycoruzkDId4leArjFpkCeH9skDqq0EDD1f23F7yulotbNAb58&_nc_ohc=q36S-FoRGykAX8aSJhN&_nc_ht=scontent.ftbs6-1.fna&oh=8e0528122c9bc5ec713eff1f9aa3a717&oe=60428AE4","სანდრო დავითაია","48 mins",3,"","","https://r4---sn-4g5e6nz7.googlevideo.com/videoplayback?expire=1612521423&ei=b8scYP7FKY22lQTl6ruwDQ&ip=2001%3Aac8%3A92%3Aa%3A0%3A1%3A67fc%3A1b7b&id=o-AB45cFJfPPDhaDsGrY4FfgBS76b8GQV-swyck8QN1Gil&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=xl9i76RjXdX7YPtuRufN9J4F&gir=yes&clen=1604776&ratebypass=yes&dur=35.898&lmt=1480250354184986&fvip=4&c=WEB&n=nePGGHrVhwb2__VZd&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgFJ-9wR-9lFhLTlorjpS4SYNNJvPfJcXlWdv0O-kgMv4CIHU3ErkNkTIq0HUX8O84xiksGGhaTSZU6QNv0AK8iUwL&rm=sn-aigezs7z&req_id=42c40a59f24a3ee&cm2rm=sn-npa3oxu-ucnz7e,sn-4g5e6d7z&ipbypass=yes&redirect_counter=3&cms_redirect=yes&mh=NN&mip=94.43.181.79&mm=34&mn=sn-4g5e6nz7&ms=ltu&mt=1612499787&mv=m&mvi=4&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgbr7Ggknfelm3DbJT3e4MosF9OmrBE6qxP1KGZizuOrkCIQCdve4pKLKutQ_EsvbZhsNbFt2oOz90oN9-TTvZ8YqpRg%3D%3D"))
        newsFeedArray.add(NewsFeedModel("https://media.thetab.com/blogs.dir/48/files/2014/02/fb-profile.gif","იოანე ქარელი","48 mins",3,"დაცვას მიხედე დაცვას","","https://r12---sn-pmcg-bg0s.googlevideo.com/videoplayback?expire=1612520647&ei=Z8gcYNTjE6PUxN8Pq4-MoAE&ip=177.22.111.249&id=o-AHge9VmVTSswGpTlsQapdM9pIt-M1NFrhgzabB-6mAcS&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=HQvJChFFXqCcr5SXxXoBOQcF&gir=yes&clen=280532&ratebypass=yes&dur=6.269&lmt=1422772549954783&fvip=12&beids=9466585&c=WEB&n=psRhoIOrUUImKQ5AM&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAJKapN166Fu_ARfKTUT3tfsgOvBlSOdluq8_Gi3he10sAiBD1hj6qu7UwoMwndOilWVYeH_6ibqVtn7-JNZdliIxPw%3D%3D&redirect_counter=1&rm=sn-n2xxqcg-h5ve7e&fexp=9466585&req_id=ee95545eefada3ee&cms_redirect=yes&mh=DT&mm=29&mn=sn-pmcg-bg0s&ms=rdu&mt=1612498838&mv=m&mvi=12&pcm2cms=yes&pl=24&lsparams=mh,mm,mn,ms,mv,mvi,pcm2cms,pl&lsig=AG3C_xAwRgIhAIigXvRlqVaFuIXnHiR4hwATMWIInM0JVEwx-K3DjZAcAiEAiyGaJ3H25G0nkF_dnEJv8pRc0v3CFX_O62-Vtyrn3c8%3D"))
        newsFeedArray.add(NewsFeedModel("https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/138185944_231412911758140_5451141139605035451_o.jpg?_nc_cat=102&ccb=2&_nc_sid=09cbfe&_nc_eui2=AeFg-4WIAq9RjSc35e3-4EeZm8Vj9qcU3KObxWP2pxTco3mIzNLQ5JDxm38qbe4zZs-pthcIva1i3H8sAglOk8Zl&_nc_ohc=pO00GBk6lKMAX8T6x1T&_nc_ht=scontent.ftbs6-1.fna&oh=949ba83811b82a7c7cd6e01bf4c07b84&oe=6041ADF6","ლუკა გურული","48 mins",2,"cu not btu","https://scontent.ftbs6-1.fna.fbcdn.net/v/t1.0-9/145531073_105600154808598_4453325681304966252_n.jpg?_nc_cat=106&ccb=2&_nc_sid=8bfeb9&_nc_eui2=AeF_UgnDTzChZsEgwsAzZ2e9yp8ghchAPObKnyCFyEA85nwLtXqM35ghrvvHnE5y5uzrX5a2wq78o6amZ4wXoa_O&_nc_ohc=Rz7U_8sW3wgAX-3xrA2&_nc_ht=scontent.ftbs6-1.fna&oh=7a2e98c2a09f12ea9e6b4c46595f7370&oe=6041CEEA",""))
        newsFeedArray.add(NewsFeedModel("https://i.ytimg.com/vi/0AsAg5ridpo/maxresdefault.jpg","ალექსანდრე კაპანაძე","48 mins",1,"200000 გადმოწერაზე ვაპატენტებთ ინსტაგრამს","",""))
        newsFeedArray.add(NewsFeedModel("https://lh3.google.com/u/0/ogw/ADGmqu_4AoYrA3-6c94XRo_gYWL8-Z4ag0gfKvMVsyo_=s83-c-mo","ზუკსონა","48 mins",3,"","","https://r2---sn-h5bupjvh-ucnl.googlevideo.com/videoplayback?expire=1612522966&ei=dtEcYLLCHZ201wLy7Ir4Aw&ip=94.240.217.236&id=o-ANP3cmmWfmrJJUgwEv3jVeTblrFgct-r8VTeC7jLQ7Bv&itag=22&source=youtube&requiressl=yes&mh=Mc&mm=31%2C26&mn=sn-h5bupjvh-ucnl%2Csn-4g5ednsy&ms=au%2Conr&mv=m&mvi=2&pl=19&gcr=ge&initcwndbps=1663750&vprv=1&mime=video%2Fmp4&ns=dyGUl5FXqTsr3dw7ZAcF0bEF&ratebypass=yes&dur=20.897&lmt=1612500924706401&mt=1612500998&fvip=2&c=WEB&txp=6216222&n=plL89KLiMIDW6vZGm&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cgcr%2Cvprv%2Cmime%2Cns%2Cratebypass%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAPTu1sgNYPqOP09LAmzSWyHbtJFwgcv-YFrsiFmVfOUEAiAru9uaV6SPMVA9xyUNhVkZYr5_M9nRwBtqb2F2QoJLEA%3D%3D&sig=AOq0QJ8wRQIgYcWMc7pcppW_IZ8rf0Zs6GATeF-uZymKYm12GDLVAjACIQDUaP6qstT4rNhf48XwyXnB91q7v-0VXgc6dPeNKX_Nig%3D%3D&title=5%20%E1%83%97%E1%83%94%E1%83%91%E1%83%94%E1%83%A0%E1%83%95%E1%83%90%E1%83%9A%E1%83%98%2C%202021"))


        val adapter = NewsFeedAdapter(this,newsFeedArray)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}