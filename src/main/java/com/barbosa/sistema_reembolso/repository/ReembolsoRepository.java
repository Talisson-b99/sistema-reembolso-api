package com.barbosa.sistema_reembolso.repository;

import com.barbosa.sistema_reembolso.domain.enums.Role;
import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.domain.model.Reembolso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReembolsoRepository extends JpaRepository<Reembolso, UUID> {
    List<Reembolso>findAllByStatus(StatusReembolso status);
}
