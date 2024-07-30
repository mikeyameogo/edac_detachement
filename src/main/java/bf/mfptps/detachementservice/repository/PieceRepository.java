package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface PieceRepository  extends JpaRepository<Piece, Long> {
}
