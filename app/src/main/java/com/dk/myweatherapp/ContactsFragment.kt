package com.dk.myweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dk.myweatherapp.data.REQUEST_CALL_PHONE
import com.dk.myweatherapp.data.REQUEST_REED_CONTACTS
import com.dk.myweatherapp.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        val permission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к контактам")
                .setMessage("Очень надо!")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    requestPermission(Manifest.permission.READ_CONTACTS, REQUEST_REED_CONTACTS)
                }
                .setNegativeButton("Не надо") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        } else {
            requestPermission(Manifest.permission.READ_CONTACTS, REQUEST_REED_CONTACTS)
        }
    }

    private fun requestPermission(permission: String, code: Int) {
        requestPermissions(arrayOf(permission), code)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_REED_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Доступ к контактам")
                    .setMessage("Объяснение")
                    .setNegativeButton("Закрыть") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }

    @SuppressLint("Range")
    private fun getContacts() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        cursorWithContacts?.let { cursor ->
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                binding.llContacts.addView(TextView(requireContext()).apply {
                    text = buildString {
                        append(name)
                        append(" : ")
                        append(number)
                    }
                    textSize = 25F
                    setOnClickListener {
                        makeCall(number)
                    }
                })
            }
        }
        cursorWithContacts?.close()
    }

    private fun makeCall(number: String?) {
        val permCallPhone =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        if (permCallPhone == PackageManager.PERMISSION_GRANTED) {
            val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intentCall)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к звонкам")
                .setMessage("Очень надо!")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    requestPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PHONE)
                }
                .setNegativeButton("Не надо") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        } else {
            requestPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PHONE)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}