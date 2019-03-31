package com.example.gym.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gym.R;
import com.example.gym.activities.ChangePasswordActivity;
import com.squareup.picasso.Picasso;

import static com.example.gym.global.GlobalVariables.user;

public class ProfileFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");

        TextView name = getView().findViewById(R.id.txt_profile_name);
        TextView email = getView().findViewById(R.id.txt_profile_email);
        TextView contact = getView().findViewById(R.id.txt_profile_contact);
        TextView address = getView().findViewById(R.id.txt_profile_address);
        TextView gender = getView().findViewById(R.id.txt_profile_gender);
        TextView birthday = getView().findViewById(R.id.txt_profile_birthday);
        ImageView image = getView().findViewById(R.id.img_profile_image);
        Button changePassword = getView().findViewById(R.id.button2);

        name.setText(user.name);
        email.setText(user.email);
        contact.setText(user.contact);
        address.setText(user.address);
        gender.setText(user.gender);
        birthday.setText(user.birthday);


        if (user.image_url.equalsIgnoreCase("")) {
            image.setBackgroundResource(R.drawable.logo);
        }else{
            Picasso.get()
                    .load(user.image_url)
                    .resize(200, 200)
                    .centerCrop()
                    .into(image);
        }

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });


    }

}
