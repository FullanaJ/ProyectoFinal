package com.fullana.proyectofinal.ui.createBill;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fullana.proyectofinal.model.ID;
import com.fullana.proyectofinal.model.Item;
import com.fullana.proyectofinal.model.bill.IDBill;
import com.fullana.proyectofinal.databinding.FragmentCreateBillBinding;

public class CreateBill extends Fragment {

    CreateBillViewModel viewModel;
    FragmentCreateBillBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CreateBillViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateBillBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        if (bundle != null) {
            IDBill idBill = (IDBill) bundle.getSerializable("id", ID.class);
            binding.recycler.setAdapter(viewModel.instanceAttributesViewModel(idBill));
            binding.recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        setOnClicks();
        setObservers();

        return binding.getRoot();
    }

    private void setObservers() {
        LifecycleOwner lo = getViewLifecycleOwner();

        viewModel.billNumber.observe(lo, (string) -> binding.billNumber.setText(string));
        viewModel.date.observe(lo, (string) -> binding.date.setText(string));
        viewModel.comments.observe(lo, string -> binding.commentClient.setText(string));
        viewModel.iva.observe(lo, integer -> {
            binding.iva.setText(integer.toString());
            Item.IVA = integer;
        });

        viewModel.client.observe(lo, client -> {
            binding.cityClient.setText(client.getCity());
            binding.addressClient.setText(client.getAddress());
            binding.nameClient.setText(client.getName());
            binding.nifClient.setText(client.getNif());
        });
        viewModel.company.observe(lo, company -> {
            binding.companyAddress.setText(company.getAddress());
            binding.companyCity.setText(company.getCity());
            binding.companyName.setText(company.getName());
            binding.companyNie.setText(company.getNie());
            binding.companyEmail.setText(company.getEmail());
            binding.companyPhone.setText(company.getPhone());
            binding.companyCeo.setText(company.getNameCeo());
            binding.companyPostalCode.setText(company.getPostalCode());
        });
    }

    private void setOnClicks() {
        Activity activity = getActivity();
        Resources resources = activity.getResources();
        binding.clientShow.setOnClickListener((l) -> viewModel.animateLayout(binding.clientLayout, viewModel.cliCollapsed, binding.clientShow, resources));
        binding.companyShow.setOnClickListener((l) -> viewModel.animateLayout(binding.companyLayout, viewModel.comCollapsed, binding.companyShow, resources));
        binding.extraDataShow.setOnClickListener((l) -> viewModel.animateLayout(binding.extraLayout, viewModel.extCollapsed, binding.extraDataShow, resources));
        binding.clientEdit.setOnClickListener((l) -> viewModel.editeClientData(activity));
        binding.companyEdit.setOnClickListener((l) -> viewModel.editeCompanyData(activity));
        binding.itemEdit.setOnClickListener((l) -> viewModel.createItem(activity));
        binding.itemShow.setOnClickListener((l) -> {
            if (!viewModel.items.getValue().isEmpty())
                viewModel.animateLayout(binding.itemLayout, viewModel.itemCollapsed, binding.itemShow, resources);
        });
        binding.createBill.setOnClickListener((l) -> viewModel.createBill(activity));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        binding.linearProgressIndicator.setProgressCompat(65, true);
    }

}