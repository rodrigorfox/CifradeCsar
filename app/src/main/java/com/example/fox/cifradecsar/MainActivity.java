package com.example.fox.cifradecsar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position){
                case 0:
                    com.example.fox.cifradecsar.Tab1Encriptar tab1 = new com.example.fox.cifradecsar.Tab1Encriptar();
                    return tab1;
                case 1:
                    com.example.fox.cifradecsar.Tab2Decriptar tab2 = new com.example.fox.cifradecsar.Tab2Decriptar();
                    return tab2;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
    public void Encriptar (View v) {
        //recebendo o texto da tela

        EditText palavra = findViewById(R.id.palavra1e);
        EditText chave = findViewById(R.id.chave1e);

        if ((palavra.getText().length() == 0) || (chave.getText().length() == 0)) {
            Toast.makeText(this, "Campos Incompletos.", Toast.LENGTH_LONG).show();
        }
        else
        {
            //recuperando e convertendo para os seus devidos formatos
            String p1 = palavra.getText().toString().toUpperCase();
            String ch = chave.getText().toString();
            int c1 = Integer.parseInt(ch);
            //cifrando
            StringBuilder textoCifrado = new StringBuilder();

            for (int c = 0; c < p1.length(); c++) {
                int letraCifradaASCII = ((int) p1.charAt(c));

                if ((letraCifradaASCII > 64) &&(letraCifradaASCII < 91)) {
                    letraCifradaASCII += (c1);
                    while (letraCifradaASCII > 90)
                        letraCifradaASCII -= 26;
                }

                textoCifrado.append((char) letraCifradaASCII);
            }

            // Por fim retorna a mensagem criptografada por completo
            String cffra = textoCifrado.toString();
            TextView t = findViewById(R.id.result);
            t.setText(cffra);
        }
    }


    public void CopiarTexto (View v) {
        TextView t = findViewById(R.id.result);
        if (t.getText().length() == 0) {
            Toast.makeText(this, "Não há item a ser copiado.", Toast.LENGTH_LONG).show();
        }
        else
        {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("texto simples", t.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"Texto copiado!",Toast.LENGTH_LONG).show();
        }
    }


    public void Decriptar (View v) {
        //recebendo o texto da tela

        EditText palavra = findViewById(R.id.palavra1d);

        if (palavra.getText().length() == 0) {
            Toast.makeText(this, "Campos Incompletos.", Toast.LENGTH_LONG).show();
        } else {
            String p1 = palavra.getText().toString().toUpperCase();
            StringBuilder texto = new StringBuilder();

            //Variáveis de obtenção da chave
            int[] numeroDeVezesTotal = new int[26];
            int maiorNumeroDeAparicoes = 0;
            int c1 = 0;


            //Conta o número de vezes que cada letra aparece
            for (int c = 0; c < p1.length(); c++) {
                int letraDecifradaASCII = ((int) p1.charAt(c));

                if ((letraDecifradaASCII > 64) &&(letraDecifradaASCII < 91)) {
                    int lt = letraDecifradaASCII-65;
                    numeroDeVezesTotal[lt] += 1;
                }
            }

            //Verifica qual letra apareceu mais e deduz a chave
            for (int i = 0; i < numeroDeVezesTotal.length; i++) {
                if (numeroDeVezesTotal[i] > maiorNumeroDeAparicoes) {
                    maiorNumeroDeAparicoes = numeroDeVezesTotal[i];
                    c1 = i;
                }
            }




            // Variavel com tamanho do texto a ser decriptado

            for (int c = 0; c < p1.length(); c++) {
                // Transforma o caracter em codigo ASCII e faz a descriptografia
                int letraDecifradaASCII = ((int) p1.charAt(c));

                if ((letraDecifradaASCII > 64) &&(letraDecifradaASCII < 91)) {
                    letraDecifradaASCII -= (c1);
                    while (letraDecifradaASCII < 65)
                        letraDecifradaASCII += 26;
                }

                texto.append((char) letraDecifradaASCII);
            }

            // Por fim retorna a mensagem descriptografada por completo
            String fn = texto.toString();
            TextView t3 = findViewById(R.id.result1d);
            t3.setText(fn);
            TextView c3 = findViewById(R.id.chave1d);
            String c2 = Integer.toString(c1);
            c3.setText(c2);
        }
    }
}
