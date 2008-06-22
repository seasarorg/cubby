SELECT m.* FROM following_members fm, member m 
WHERE fm.member_id = m.member_id
AND fm.following_member_id = /*memberId*/3
AND fm.request = true
