package com.ptlms.controller;

import com.ptlms.entity.Member;
import com.ptlms.entity.User;
import com.ptlms.entity.WorkoutLog;
import com.ptlms.entity.WorkoutPart;
import com.ptlms.repository.MemberRepository;
import com.ptlms.repository.UserRepository;
import com.ptlms.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TRAINER')")  // ★ 트레이너만 접근 가능
public class TrainerController {

    private final WorkoutLogService workoutLogService;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    // ★ 담당 회원 목록 조회
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMyMembers(@AuthenticationPrincipal UserDetails userDetails) {
        User trainer = getTrainerFromUser(userDetails);
        List<Member> members = memberRepository.findByTrainerIdWithUser(trainer.getId());
        return ResponseEntity.ok(members);
    }

    // ★ 담당 회원들의 최근 운동 기록
    @GetMapping("/workout/recent")
    public ResponseEntity<List<WorkoutLog>> getRecentWorkoutLogs(@AuthenticationPrincipal UserDetails userDetails) {
        User trainer = getTrainerFromUser(userDetails);
        List<WorkoutLog> logs = workoutLogService.getRecentLogsByTrainer(trainer.getId());
        return ResponseEntity.ok(logs);
    }

    // 특정 회원의 운동 기록 조회
    @GetMapping("/members/{memberId}/workout")
    public ResponseEntity<List<WorkoutLog>> getMemberWorkoutLogs(@PathVariable Long memberId) {
        List<WorkoutLog> logs = workoutLogService.getMyWorkoutLogs(memberId);
        return ResponseEntity.ok(logs);
    }

    // ★ 트레이너 피드백 작성
    @PostMapping("/workout/{logId}/feedback")
    public ResponseEntity<WorkoutLog> addFeedback(
            @PathVariable Long logId,
            @RequestBody String feedback) {
        WorkoutLog log = workoutLogService.addTrainerFeedback(logId, feedback);
        return ResponseEntity.ok(log);
    }

    // ★ 회원 운동 기록 수정
    @PutMapping("/workout/{logId}")
    public ResponseEntity<WorkoutLog> updateWorkoutLog(
            @PathVariable Long logId,
            @RequestParam(required = false) WorkoutPart workoutPart,
            @RequestParam(required = false) String workoutDetail,
            @RequestParam(required = false) Integer sets,
            @RequestParam(required = false) Integer durationMinutes) {
        WorkoutLog log = workoutLogService.updateWorkoutLog(logId, workoutPart, workoutDetail, sets, durationMinutes);
        return ResponseEntity.ok(log);
    }

    private User getTrainerFromUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("트레이너 정보를 찾을 수 없습니다."));
    }
}
