SELECT m.member_id, m.member_name, m.full_name FROM following_members fm, member m
WHERE fm.member_id = /*memberId*/1
AND fm.following_member_id = m.member_id
AND fm.request = false
ORDER BY m.member_id