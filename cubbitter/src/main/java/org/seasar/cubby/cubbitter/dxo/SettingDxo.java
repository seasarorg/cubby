package org.seasar.cubby.cubbitter.dxo;

/**
 * SettingDtoからMemberへの変換用クラス
 */

import org.seasar.cubby.cubbitter.dto.SettingDto;
import org.seasar.cubby.cubbitter.entity.Member;

public interface SettingDxo {
	void convert(SettingDto from, Member to);
}
